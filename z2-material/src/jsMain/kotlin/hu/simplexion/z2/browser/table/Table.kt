/*
 * Copyright © 2020-2023, Simplexion, Hungary and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package hu.simplexion.z2.browser.table

import hu.simplexion.z2.browser.material.html.*
import hu.simplexion.z2.browser.util.downloadCsv
import hu.simplexion.z2.browser.util.getDatasetEntry
import hu.simplexion.z2.browser.util.io
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.datetime.*
import kotlinx.dom.clear
import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.dom.events.MouseEvent
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min

/**
 * Table for the browser frontend.
 *
 * @property  query           When initialized, the table automatically executes this query during onResume to fill
 *                            the table with data.
 * @property  addLocalTitle   When true, add a local title bar. Default is false.
 * @property  titleText       Title text to show in the title bar. Used when [titleElement] is not set.
 * @property  titleElement    The element of the title.
 * @property  columns         Column definitions.
 * @property  preloads        Data load jobs which has to be performed before the table is rendered.
 */
open class Table<T>(
    var query: (() -> List<T>)? = null,
    val configuration: TableConfiguration = TableConfiguration()
) {

    val columns = mutableListOf<TableColumn<T>>()

    open val exportFileName: String
        get() {
            // FIXME val prefix = (titleText ?: this::class.simpleName?.localized ?: "").ifBlank { "content" }
            val prefix = "content"
            val time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return "${prefix}_$time".replace("\\W".toRegex(), "_") + ".csv"
        }

    // open var counterBar = ZkCounterBar("")

    var traceScroll = false
    var traceMultiLevel = true

    open var firstOnResume = true

    val rowHeight
        get() = configuration.rowHeight

    // -------------------------------------------------------------------------
    //  DOM
    // -------------------------------------------------------------------------

    lateinit var outerContainer: Z2
    lateinit var contentContainer: Z2
    lateinit var tableElement: HTMLTableElement
    lateinit var tableBodyElement : HTMLTableSectionElement

    // gap before the first row, used to virtualize rows

    val placeHolderCell = document.createElement("td") as HTMLTableCellElement
    val placeHolderRow = (document.createElement("tr") as HTMLTableRowElement).also { it.appendChild(placeHolderCell) }

    var contentScrollTop: Double = 0.0
    var contentScrollLeft: Double = 0.0

    // Reference: http://www.html5rocks.com/en/tutorials/speed/animations/
    var lastKnownScrollPosition = 0.0
    var ticking = false

    var firstAttachedRowIndex = 0
    var attachedRowCount = 0

    /** HTMLElement.dataset key to store the ID of the row, never changes, calculated by [getRowId]  */
    open val ROW_ID = "zkrid"

    /** HTMLElement.dataset key to store the index of the row, the index shows the row index in [renderData] */
    open val ROW_INDEX = "zkridx"

    /** HTMLElement.dataset key to indicte that this is a level toggle for a multi-level row */
    open val LEVEL_TOGGLE = "zklt"

    open val addAboveCount: Int
        get() = window.outerHeight / rowHeight

    open val addBelowCount: Int
        get() = window.outerHeight / rowHeight

    // -------------------------------------------------------------------------
    //  State -- data of the table, search text, preloaded data
    // -------------------------------------------------------------------------

    /** All the data behind the table (visible and hidden together). */
    lateinit var fullData: List<TableRow<T>>

    /** Filtered [fullData] (if there is a filter, equals to [fullData] otherwise).
     *  contains all child rows in case of multi-level table.
     */
    open lateinit var filteredData: List<TableRow<T>>

    /** The data to show to the user, filtered and may be closed (for multi-level tables). */
    open lateinit var renderData: MutableList<TableRow<T>>

    // FIXME val preloads = mutableListOf<ZkTablePreload<*>>()

    open var searchText: String? = null

    open var allCount: Int? = null // if the full data size of the very first query is not equal to all count, it is possible to set here

    open var needToSetAllCounter = true

    fun Z2.main() {
        div("table-outer-container") {
            outerContainer = this
            titleBar()

            div("table-content-container") {
                contentContainer = this

                table("table") {
                    style.cssText = inlineCss()

                    thead("no-select") {
                        for(column in columns) {
                            with(column) { header(configuration) }
                        }
                    }

                    tbody {
                        tableBodyElement = this
                    }
                }
            }

            on("scroll") { onScroll() }
        }

        if (configuration.counter) {
            // TODO + counterBar
        }

        on("mousedown", ::onMouseDown)
        on("dblclick", ::onDblClick)
        on("click", ::onClick)
    }

    fun resume() {

        val query = this.query

        when {
            query != null && (configuration.runQueryOnResume || firstOnResume) -> {
                setData(emptyList())
                io {
                    setData(query()) // calls render
                }
            }

            // this means that setData has been called before onResume
            ::fullData.isInitialized && firstOnResume -> {
                render()
            }
        }

        if (! firstOnResume) {
            window.requestAnimationFrame {
                contentContainer.scrollTo(contentScrollLeft, contentScrollTop)
            }
        }

        firstOnResume = false

    }

    fun Z2.titleBar(): Z2 =
        div {
            if (!configuration.titleBar) return@div
        }

//    fun titleActions(): List<ZkElement> {
//
//        val actions = mutableListOf<ZkElement>()
//
//        if (add) actions += ZkAddRowAction(::onAddRow)
//        if (export) actions += ZkExportCsvAction(::onExportCsv)
//        if (search) actions += ZkSearchAction(searchText ?: "", ::onSearch)
//
//        return actions
//    }

    open fun setCounter() {

//        if (needToSetAllCounter && allCount == null && ::fullData.isInitialized && fullData.isNotEmpty()) {
//            allCount = fullData.size
//            needToSetAllCounter = false
//        }
//
//        val all = allCount ?: ""
//        val count = if (::filteredData.isInitialized) filteredData.size.toString() else all
//
//        counterBar.text = "${localizedStrings.counterTitle} $count/$all"
//        counterBar.onCreate()

    }

    // -------------------------------------------------------------------------
    //  Event Handlers
    // -------------------------------------------------------------------------

    open fun getRowId(event: Event): String? {
        if (event !is MouseEvent) return null

        val target = event.target
        if (target !is HTMLElement) return null

        return target.getDatasetEntry(ROW_ID)
    }

    open fun onClick(event: Event) {
        val target = event.target

        if (target is HTMLElement && target.getDatasetEntry(LEVEL_TOGGLE) != null) {
            toggleMultiLevelRow(renderData[target.getDatasetEntry(ROW_INDEX) !!.toInt()])
            return
        }

        if (configuration.oneClick) getRowId(event)?.let { onDblClick(it) }
    }

    /**
     * Prevent text selection on double click.
     */
    open fun onMouseDown(event: Event) {
        event as MouseEvent
        if (event.detail > 1) {
            event.preventDefault()
        }
    }

    open fun onDblClick(event: Event) {
        event.preventDefault()
        getRowId(event)?.let { onDblClick(it) }
    }

    open fun onScroll() {
        contentScrollTop = contentContainer.scrollTop
        contentScrollLeft = contentContainer.scrollLeft

        lastKnownScrollPosition = contentContainer.scrollTop.let { if (it < 0) 0.0 else it } // Safari may have negative scrollTop

        if (! ticking) {
            window.requestAnimationFrame {
                onScroll(lastKnownScrollPosition)
                ticking = false
            }
            ticking = true
        }
    }

    // -------------------------------------------------------------------------
    //  Data setter, preload
    // -------------------------------------------------------------------------

    //fun <RT : Any> preload(loader: suspend () -> RT) = ZkTablePreload(loader)

    /**
     * Set data of the table. Asynchronous, waits for the preloads
     * to finish before the data is actually set.
     *
     * @param  data  The data to set.
     */
    fun setData(data: List<T>): Table<T> {
        io {
//            preloads.forEach {
//                it.job.join()
//            }

            fullData = data.mapIndexed { index, bo -> TableRow(index, bo) }

            buildMultiLevelState()

            columns.forEach { it.onTableSetData() }

            filter()

            render()
        }

        return this
    }

    /**
     * Builds the states for multi level rows if multi level option is enabled.
     * By default, all top-level rows are closed.
     */
    open fun buildMultiLevelState() {
        if (! configuration.multiLevel) return
        if (fullData.isEmpty()) return

        var previousLevel = 0
        check(getRowLevel(fullData[0]) == 0) { "the first row must be level 0" }

        fullData.forEachIndexed { index, row ->

            val level = getRowLevel(row)

            if (previousLevel < level) {
                fullData[index - 1].levelState = TableRowLevelState.Closed
            }

            row.level = level
            previousLevel = level
        }
    }

    /**
     * Execute the given query and set the table data to its
     * result. Asynchronous, waits for the preloads to finish
     * before the data is actually set.
     *
     * Calls the list version of [setData] with the query result.
     *
     * @param  query  The query to execute
     */
    open fun setData(query: suspend () -> List<T>) {
        io {
            setData(query())
        }
    }

    // -------------------------------------------------------------------------
    //  Rendering, intersection observer callback
    // -------------------------------------------------------------------------

    open fun inlineCss() = """
        grid-template-columns: ${columns.joinToString(" ") { it.gridTemplate() }};
    """.trimIndent()

    open fun render() {
        firstAttachedRowIndex = 0
        attachedRowCount = min(renderData.size, addBelowCount)

        redraw()
    }

    /**
     * Redraws the currently shown rows of the table.
     * Deletes all cached row renders.
     * Sets scroll position to the latest known value
     */
    open fun redraw(): Table<T> {

        for (row in fullData) {
            row.element = null
        }

        window.requestAnimationFrame {

            tableBodyElement.clear()

            addPlaceHolderRow()
            adjustTopPlaceHolder()

            addPlaceHolderRow()
            adjustBottomPlaceHolder()

            attach(firstAttachedRowIndex, attachedRowCount)

            // if (counter) setCounter()

            // contentContainer.element.scrollTo(contentScrollLeft, contentScrollTop)

        }

        return this
    }

    /**
     * Adds a placeholder row (top or bottom). These rows change in height so
     * the scrollbar reacts as if all rows would be rendered.
     */
    open fun addPlaceHolderRow() {
        val row = tableBodyElement.appendChild(document.createElement("tr"))
        repeat(columns.size) {
            row.appendChild(document.createElement("td")).also { cell ->
                cell as HTMLTableCellElement
                cell.style.border = "none"
                cell.style.padding = "0px"
            }
        }
    }

    /**
     * Renders the row. Caches the result and returns with it for subsequent calls
     * on the same row.
     *
     * @param index Index of the row in state.rowStates
     */
    open fun TableRow<T>.render(index: Int, row: TableRow<T>) : Z2 {
        element?.let { return it }

        val heightClass = if (configuration.fixRowHeight) "table-fix-height" else "table-variable-height"

        val tr = document.createElement("tr") as HTMLElement

        with(tr) {
            for (column in columns) {
                td("table-cell", heightClass) {
                    column.render(this, index, row.data)
                }
            }
        }

        tr.dataset[ROW_ID] = getRowId(row.data)
        tr.dataset[ROW_INDEX] = index.toString()

        element = tr

        return tr
    }

    // -------------------------------------------------------------------------
    //  Utility functions for scroll
    // -------------------------------------------------------------------------

    /**
     * Attaches a row to the table. Attached rows are not necessarily visible for
     * the user (they may be scrolled out) but they are added to the DOM.
     */
    fun attach(index: Int) {
        // Get the state of the row or create a new one if it doesn't exist yet
        val rowState = renderData[index]

        // Render and attach the row
        rowState.apply {
            render(index, this).also {
                var row = tableBodyElement.firstElementChild as HTMLTableRowElement?
                var added = false
                while (row != null) {
                    val ridx = row.dataset[ROW_INDEX]?.toInt()
                    if (ridx != null && ridx > index) {
                        tableBodyElement.insertBefore(it, row)
                        added = true
                        break
                    }
                    row = row.nextElementSibling as HTMLTableRowElement?
                }

                if (! added) {
                    tableBodyElement.insertBefore(it, tableBodyElement.lastElementChild)
                }
            }
        }
    }

    fun attach(start: Int, count: Int) {
        (start until start + count).forEach {
            attach(it)
        }
    }

    fun remove(index: Int): Double {
        return renderData[index].let { state ->
            state.element !!.remove()
            state.height !!
        }
    }

    fun remove(start: Int, end: Int): Double {
        return (start until end).sumOf { offset -> remove(offset) }
    }

    fun removeAll() {
        val start = firstAttachedRowIndex
        val end = start + attachedRowCount

        (start until end).forEach { index ->
            remove(index)
        }
    }

    fun adjustTopPlaceHolder() {
        var placeHolderCell = tableBodyElement.firstElementChild?.firstElementChild as HTMLTableCellElement?
        val placeHolderHeight = "${rowHeight * firstAttachedRowIndex}px"
        while (placeHolderCell != null) {
            placeHolderCell.style.minHeight = placeHolderHeight
            placeHolderCell.style.height = placeHolderHeight
            placeHolderCell = placeHolderCell.nextElementSibling as HTMLTableCellElement?
        }
    }

    fun adjustBottomPlaceHolder() {
        var placeHolderCell = tableBodyElement.lastElementChild?.firstElementChild as HTMLTableCellElement?
        val placeHolderHeight = "${rowHeight * (renderData.size - (firstAttachedRowIndex + attachedRowCount))}px"
        while (placeHolderCell != null) {
            placeHolderCell.style.minHeight = placeHolderHeight
            placeHolderCell.style.height = placeHolderHeight
            placeHolderCell = placeHolderCell.nextElementSibling as HTMLTableCellElement?
        }
    }

    /**
     * @return Height of the area that shows rows.
     */
    fun viewHeight(): Double {
        val containerHeight = contentContainer.getBoundingClientRect().height
        val headerHeight = tableElement.firstElementChild?.firstElementChild?.getBoundingClientRect()?.height ?: rowHeight.toDouble()

        // if (trace) println("contentContainer height: $containerHeight thead height: $headerHeight")

        // TODO subtract footer if needed
        return containerHeight - headerHeight
    }

    /**
     * @return Height of the currently attached rows (in pixels).
     */
    fun attachedHeight(): Double {
        val start = firstAttachedRowIndex
        val end = start + attachedRowCount

        return (start until end).sumOf { index ->
            renderData[index].let { state ->
                state.height ?: state.element !!.firstElementChild !!.getBoundingClientRect().height.also { state.height = it }
            }
        }
    }

    // -------------------------------------------------------------------------
    //  Scroll handler
    // -------------------------------------------------------------------------

    /**
     * Calculates the empty area shown on the screen and calls the appropriate function
     * to fill it.
     */
    fun onScroll(scrollTop: Double) {
        val viewHeight = viewHeight() // height of the area that shows rows
        val attachedHeight = attachedHeight()

        val topRowCount = firstAttachedRowIndex

        val topBoundary = rowHeight * topRowCount     // offset to the start of the first rendered row
        val bottomBoundary = topBoundary + attachedHeight      // offset to the end of the last rendered row

        if (traceScroll) println("scrollTop: $scrollTop viewHeight: $viewHeight attachedHeight: $attachedHeight topBoundary: $topBoundary, bottomBoundary: $bottomBoundary")

        when {
            // above attached rows, no attached row on screen
            scrollTop + viewHeight < topBoundary -> fullEmpty(scrollTop, attachedHeight)

            // below attached rows, no attached row on screen
            scrollTop > bottomBoundary -> fullEmpty(scrollTop, attachedHeight)

            // above attached rows, EMPTY area between attached rows and scrollTop
            scrollTop < topBoundary -> partialEmptyAbove(scrollTop, attachedHeight)

            // below attached rows, EMPTY area between attached rows and scrollTop
            scrollTop < bottomBoundary && bottomBoundary < scrollTop + viewHeight -> partialEmptyBelow(scrollTop)

            // there is no empty area
            else -> noEmpty()
        }
    }

    fun fullEmpty(scrollTop: Double, originalAttachedHeight: Double) {
        if (traceScroll) println("fullEmpty")

        removeAll()

        val currentTotalHeight = renderData.size * rowHeight - (attachedRowCount * rowHeight) + originalAttachedHeight
        val scrollPercentage = scrollTop / currentTotalHeight
        firstAttachedRowIndex = floor(renderData.size * scrollPercentage).toInt()
        attachedRowCount = min(renderData.size - firstAttachedRowIndex, addBelowCount)

        if (traceScroll) println("currentTotalHeight: $currentTotalHeight  scrollTop: $scrollTop firstAttachedRowIndex: $firstAttachedRowIndex attachedRowCount: $attachedRowCount")

        attach(firstAttachedRowIndex, attachedRowCount)

        adjustTopPlaceHolder()
        adjustBottomPlaceHolder()
        contentContainer.scrollTo(0.0, firstAttachedRowIndex * rowHeight.toDouble())
    }

    fun partialEmptyAbove(scrollTop: Double, originalAttachedHeight: Double) {
        if (traceScroll) println("partialEmptyAbove")

        // Calculate the number of rows to attach, update fields, attach the rows

        val originalFirstAttachedRowIndex = firstAttachedRowIndex
        val originalAttachedRowCount = attachedRowCount

        firstAttachedRowIndex = max(originalFirstAttachedRowIndex - addAboveCount, 0)
        attachedRowCount = originalAttachedRowCount + (originalFirstAttachedRowIndex - firstAttachedRowIndex)

        attach(firstAttachedRowIndex, attachedRowCount - originalAttachedRowCount)

        // Calculate new attached height, so we can adjust the scroll top. This adjustment is
        // necessary because the actual height of the attached rows may be different from
        // the estimated row height. This difference would cause the rows already shown to
        // change position on the screen, we don't want that.

        val newAttachedHeight = attachedHeight()

        val estimatedAddition = (originalFirstAttachedRowIndex - firstAttachedRowIndex) * rowHeight
        val actualAddition = newAttachedHeight - originalAttachedHeight

        contentContainer.scrollTop = scrollTop + (actualAddition - estimatedAddition)

        adjustTopPlaceHolder()

        if (traceScroll) println("newAttachedHeight: $newAttachedHeight oldAttachedHeight: $originalAttachedHeight actualAddition : $actualAddition estimatedAddition : $estimatedAddition")

        // Remove rows from the bottom if there are too many attached

        val limit = addBelowCount * 3

        if (attachedRowCount > limit) {

            remove(firstAttachedRowIndex + limit, firstAttachedRowIndex + attachedRowCount)

            attachedRowCount = limit

            adjustBottomPlaceHolder()

            if (traceScroll) println("limit adjustment: start: ${firstAttachedRowIndex + limit} end: ${firstAttachedRowIndex + attachedRowCount} limit : $limit estimatedAddition : $estimatedAddition")
        }
    }

    fun partialEmptyBelow(scrollTop: Double) {
        if (traceScroll) println("partialEmptyBelow")

        // Calculate the number of rows to attach, update fields, attach the rows

        val originalAttachedRowCount = attachedRowCount

        attachedRowCount = min(attachedRowCount + addBelowCount, renderData.size - firstAttachedRowIndex)

        attach(firstAttachedRowIndex + originalAttachedRowCount, attachedRowCount - originalAttachedRowCount)

        adjustBottomPlaceHolder()

        if (traceScroll) println("newAttachedRowCount: $attachedRowCount originalAttachedRowCount: $originalAttachedRowCount")

        // Remove rows from the top if there are too many attached

        val limit = addAboveCount * 3

        if (attachedRowCount > limit) {
            val start = firstAttachedRowIndex
            val end = firstAttachedRowIndex + attachedRowCount - limit

            val actualRemoval = remove(start, end)

            firstAttachedRowIndex = end
            attachedRowCount = limit

            // Calculate new attached height, so we can adjust the scroll top. This adjustment is
            // necessary because the actual height of the removed rows may be different from
            // the estimated row height. This difference would cause the rows still shown to
            // change position on the screen, we don't want that.

            val estimatedRemoval = (end - start) * rowHeight

            if (actualRemoval - estimatedRemoval > 0.5) {
                contentContainer.scrollTop = scrollTop - (actualRemoval - estimatedRemoval)
            }

            adjustTopPlaceHolder()

            if (traceScroll) println("limit adjustment: start: $start end: $end limit : $limit estimatedRemoval : $estimatedRemoval actualRemoval: $actualRemoval")
        }
    }

    fun noEmpty() {
        if (traceScroll) println("noEmpty")
    }

    // -------------------------------------------------------------------------
    //  API functions, intended for override
    // -------------------------------------------------------------------------

    /**
     * Get a unique if for the given row. The id of the row is used by actions and is
     * passed to row based functions such as [onDblClick].
     */
    open fun getRowId(row: T): String {
        // FIXME getRowId
        throw NotImplementedError("please override ${this::class}.getRowId when not using crud")
    }

    /**
     * Get the data of a given row by row ID.
     */
    open fun getRowData(id: String): T =
        fullData.first { getRowId(it.data) == id }.data

    /**
     * Set the data of a given row by row ID. Does not update the UI,
     * call [redraw] for that.
     */
    open fun setRowData(data: T, optional: Boolean = false) {
        val id = getRowId(data)
        if (optional) {
            fullData.firstOrNull { getRowId(it.data) == id }?.data = data
        } else {
            fullData.first { getRowId(it.data) == id }.data = data
        }
    }

    /**
     * Add a new row to the table.
     */
    open fun onAddRow() {

    }

    /**
     * Handle double click on a row.
     *
     * @param  id  Id of the row as given by [getRowId].
     */
    open fun onDblClick(id: String) {

    }

    /**
     * Performs a search on the table, showing only rows that contain [text].
     *
     * Default implementation:
     *
     * * sets [searchText] to [text]
     * * calls [filter]
     * * calls [render]
     */
    open fun onSearch(text: String) {
        searchText = text.ifEmpty { null }
        filter()
        render()
    }

    /**
     * Exports the table to CSV.
     *
     * Default implementation:
     *
     * * exports all the data, not the filtered state, can be changed with [exportFiltered]
     * * calls [ZkColumn.exportCsv] for each row to build the csv line
     * * pops a download in the browser with the file name set to [exportFileName]
     */
    open fun onExportCsv() {
        val lines = mutableListOf<String>()

        val data = if (configuration.exportFiltered) filteredData else fullData

        if (configuration.exportHeaders) {
            val fields = mutableListOf<String>()
            columns.forEach { if (it.exportable) fields += it.exportCsvHeader() }
            lines += fields.joinToString(";")
        }

        data.forEach { row ->
            val fields = mutableListOf<String>()
            columns.forEach { if (it.exportable) fields += it.exportCsv(row.data) }
            lines += fields.joinToString(";")
        }

        val csv = lines.joinToString("\n")

        downloadCsv(exportFileName, csv)
    }

    /**
     * Applies all filters on the table rows.
     */
    open fun filter() {

        // when there is no filter return with all the data
        // multi-level table needs special processing because of the hidden
        // rows, so check for that also

        if (searchText == null && ! configuration.multiLevel) {
            filteredData = fullData
            renderData = filteredData.toMutableList()
            return
        }

        val lc = searchText?.lowercase()

        // when not multi-level, perform single filtering

        if (! configuration.multiLevel) {
            filteredData = fullData.filter { filterRow(it.data, lc) }
            renderData = filteredData.toMutableList()
            return
        }

        // multi-level rows need special filtering:
        // - a top level row matches: add it and add all children
        // - a child row matches: add the top level row and all the children

        val filterResult = mutableListOf<TableRow<T>>()
        val renderResult = mutableListOf<TableRow<T>>()
        var index = 0

        while (index < fullData.size) {
            val row = fullData[index]
            var match = (searchText == null || filterRow(row.data, lc))

            if (row.levelState == TableRowLevelState.Single) {
                println("$index ${row.levelState} ${row.level}")
                if (match) {
                    filterResult += row
                    renderResult += row
                }
                index += 1
                continue
            }

            val children = getChildren(fullData, index, row.level)

            if (! match) match = (children.firstOrNull { filterRow(it.data, lc) } != null)

            if (match) {
                filterResult += row
                filterResult += children
                renderResult += row
                println("$index ${row.levelState} ${row.level} ${children.size}")
                if (row.levelState == TableRowLevelState.Open) {
                    renderResult += children
                }
            }

            index += 1 + children.size
        }

        filteredData = filterResult
        renderData = renderResult

        if (traceMultiLevel) println("filter: filteredData.size = ${filteredData.size}  renderData.size = ${renderData.size}")
    }

    /**
     * Applies the filters to one row to decide if that row should be
     * present in the filtered table.
     */
    open fun filterRow(row: T, text: String?): Boolean {
        columns.forEach {
            if (it.matches(row, text)) return true
        }
        return false
    }

    /**
     * Get the level of the row. Override for multi level tables. Level 0
     * means that the row is always shown (if not filtered out).
     */
    open fun getRowLevel(row: TableRow<T>): Int {
        return 0
    }

    open fun toggleMultiLevelRow(row: TableRow<T>) {
        if (row.levelState == TableRowLevelState.Closed) {
            openMultiLevelRow(row)
        } else {
            closeMultiLevelRow(row)
        }
    }

    /**
     * When opening a multi-level row, we have to add the child rows from [filteredData]
     * to [renderData].
     */
    open fun openMultiLevelRow(row: TableRow<T>) {

        if (traceMultiLevel) println("openMultiLevel ${row.index}")

        val renderIndex = renderData.indexOf(row)
        val children = getChildren(filteredData, filteredData.indexOf(row), row.level)

        renderData.addAll(renderIndex + 1, children)

        row.levelState = TableRowLevelState.Open

        attachedRowCount += children.size

        redraw() // FIXME this may not cover the whole area - maybe
    }

    open fun closeMultiLevelRow(row: TableRow<T>) {

        if (traceMultiLevel) println("closeMultiLevel ${row.index}")

        val renderIndex = renderData.indexOf(row)
        val children = getChildren(filteredData, filteredData.indexOf(row), row.level)

        repeat(children.size) {
            renderData.removeAt(renderIndex + 1)
        }

        row.levelState = TableRowLevelState.Closed

        attachedRowCount -= children.size

        // When there are many children, we may have to put back non-children rows
        // at close. Otherwise, there could be an empty area under the closed
        // row.

        if (attachedRowCount - (renderIndex - firstAttachedRowIndex) < addBelowCount) {
            attachedRowCount += addBelowCount
        }

        redraw()
    }

    open fun getChildren(from: List<TableRow<T>>, fromIndex: Int, level: Int): List<TableRow<T>> {
        var index = fromIndex + 1
        val children = mutableListOf<TableRow<T>>()

        while (index < from.size) {
            val next = from[index]

            if (next.level > level) {
                children += next
                index ++
            } else {
                break
            }
        }

        return children
    }

    // -------------------------------------------------------------------------
    //  Sorting
    // -------------------------------------------------------------------------

    open fun <R : Comparable<R>> sort(ascending: Boolean, comparison: (row: TableRow<T>) -> R?) {
        fullData = if (! configuration.multiLevel) {
            if (ascending) {
                fullData.sortedBy(comparison)
            } else {
                fullData.sortedByDescending(comparison)
            }
        } else {
            multiSort(fullData, ascending, comparison)
        }
    }

    /**
     * Utility class for multi-level sorting. Contains the children of the
     * row, so they will move together when sorted.
     */
    class SortEntry<T>(
        val row: TableRow<T>,
        val children: List<TableRow<T>>
    )

    /**
     * Sort a multi-level list of rows. Calls itself recursively for lower levels.
     */
    open fun <R : Comparable<R>> multiSort(data: List<TableRow<T>>, ascending: Boolean, comparison: (row: TableRow<T>) -> R?): List<TableRow<T>> {

        // Do not perform sorting when the data contains a single row.
        if (data.size == 1) return data

        // Collect top level rows into a list of SortEntry objects. All lower level rows
        // are stored in SortEntry.children. Thus, we can sort the top level and lower
        // level rows will move with their parents.

        // Call multiSort recursively on children, so they are also sorted.

        val top = mutableListOf<SortEntry<T>>()
        var index = 0

        while (index < data.size) {
            val row = data[index]

            if (row.levelState == TableRowLevelState.Single) {
                top += SortEntry(row, emptyList())
            } else {
                val children = getChildren(data, index, row.level)
                top += SortEntry(row, multiSort(children, ascending, comparison))
                index += children.size
            }

            index += 1
        }

        // Sort the top level rows and merge all rows together to get the result.

        val sortedTop = if (ascending) {
            top.sortedBy { comparison(it.row) }
        } else {
            top.sortedByDescending { comparison(it.row) }
        }

        val result = mutableListOf<TableRow<T>>()

        sortedTop.forEach {
            result += it.row
            result += it.children
        }

        return result
    }

}