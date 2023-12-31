' ********************************************************************************************************
' ********************************************************************************************************
' **  Roku Moon Patrol Channel - http://github.com/lvcabral/Moon-Patrol-Roku
' **
' **  libListScreen.brs - Library to implement generic List Screen
' **  Created: June 2018
' **  Updated: July 2019
' **
' **  Copyright (C) Marcelo Lv Cabral < https://lvcabral.com >
' ********************************************************************************************************
' ********************************************************************************************************

Function CreateListScreen(ignoreBackKey = false as boolean) as object
    ' Objects
    this = {content:[], canvas: CreateCanvas()}
    this.screen = this.canvas.screen
    this.codes = m.code
    this.sounds = m.sounds
    this.theme = m.theme
    this.ignoreBackKey = ignoreBackKey

    ' Properties
    this.headerText = ""
    this.breadCrumb = []
    this.focus = 0
    this.first = 0
    this.last = 0
    this.visible = false

    ' Methods
    this.SetMessagePort = set_msg_port
    this.SetBreadcrumbText = set_breadcrumb_text
    this.SetHeader = set_list_header
    this.SetContent = set_list_content
    this.SetItem = set_content_item
    this.SetFocusedListItem = set_focused_item
    this.Show = show_list_screen
    this.Wait = wait_list_screen
    this.Close = close_screen

    ' Initialize Cache
    InitCache()

    ' Initialize Canvas
    this.canvas.SetLayer(0, GetOverhang())

    return this
End Function

Sub show_list_screen()
    print "Show List"
    txtArray = []
    imgArray = []
    txtArray.Append(m.breadCrumb)
    txtArray.Push({
                Text: m.headerText
                TextAttrs: {color: m.theme.ListScreenHeaderText, font: "Large", HAlign: "Left"}
                TargetRect: {x:170, y:156, w:524, h:32}})
    if m.content.Count() > 0           
        imgArray.Push({
                    url: m.content[m.focus].HDPosterUrl 'CachedFile(m.content[m.focus].HDPosterUrl + "250x250")
                    TargetRect: {x: 782, y: 200}})
        txtArray.Push({
                    Text: m.content[m.focus].ShortDescriptionLine1
                    TextAttrs: {color: m.theme.ListScreenDescriptionText, font: "Medium", HAlign: "Center"}
                    TargetRect: {x:708, y:510, w:450, h:60}})
        txtArray.Push({
                    Text: m.content[m.focus].ShortDescriptionLine2
                    TextAttrs: {color: m.theme.ListScreenDescriptionText, font: "Small", HAlign: "Center"}
                    TargetRect: {x:708, y:580, w:450, h:60}})
        menuPos = {x: 226, y: 220}
        if m.first > 0
            imgArray.Push({
                        url: "pkg:/images/arrow-up.png"
                        TargetRect: {x: 380, y: menuPos.y-40}})
        end if
        for i = m.first to m.last
            if i = m.focus
                if m.theme.ListItemHighlightHD <> invalid and m.theme.ListItemHighlightHD <> ""
                    urlBar = m.theme.ListItemHighlightHD
                else
                    urlBar = "pkg:/images/list-bar.png"
                end if
                imgArray.Push({ url: urlBar
                                TargetRect: {x: menuPos.x-28 , y: menuPos.y-4}})
                textColor = m.theme.ListItemHighlightText
            else
                textColor = m.theme.ListItemText
            end if
            txtArray.Push({
                        Text: m.content[i].Title
                        TextAttrs: {color: textColor, font: "Medium", HAlign: "Left"}
                        TargetRect: {x:menuPos.x, y:menuPos.y, w:420, h:30}})
            imgArray.Push({
                        url: m.content[i].HDSmallIconUrl
                        TargetRect: {x: 654, y: menuPos.y}})
            menuPos.y = menuPos.y + 54
        next
        if m.last < m.content.Count() - 1
            imgArray.Push({
                        url: "pkg:/images/arrow-down.png"
                        TargetRect: {x: 380, y: menuPos.y-33}})
        end if
    end if
    m.canvas.SetLayer(1, imgArray)
    m.canvas.SetLayer(2, txtArray)
    m.canvas.Show()
    m.visible = true
End Sub

Sub set_list_content(list as object)
    m.content = list
    for i = 0 to m.content.Count() - 1
        m.content[i].HDPosterUrl = CenterImage(m.content[i].HDPosterUrl, 300, 300)
    next
    m.first = 0
    m.focus = 0
    if m.content.Count() > 9
        m.last = 7
    else
        m.last = m.content.Count() - 1
    end if
    if m.visible then m.Show()
End Sub

Sub set_content_item(index as integer, item as object, refresh = true as boolean)
    item.HDPosterUrl = CenterImage(item.HDPosterUrl, 300, 300)
    m.content[index] = item
    if m.visible and refresh then m.Show()
End Sub

Function wait_list_screen(port) as object
    if port = invalid then port = m.canvas.screen.port
    while true
        event = wait(0, port)
        if type(event) = "roUniversalControlEvent"
            index = event.GetInt()
            if index = m.codes.BUTTON_UP_PRESSED
                if m.content.Count() > 0
                    if m.focus = 0
                        m.sounds.deadend.Trigger(50)
                    else
                        m.focus--
                        if m.first > 0 and m.focus < m.first
                            m.first--
                            m.last--
                        end if
                        m.sounds.navSingle.Trigger(50)
                        m.Show()
                        msg = GetScreenMessage(m.focus, "focused")
                        exit while
                    end if
                end if
            else if index = m.codes.BUTTON_DOWN_PRESSED
                if m.content.Count() > 0
                    if m.focus = m.content.Count() - 1
                        m.sounds.deadend.Trigger(50)
                    else
                        m.focus++
                        if m.last < (m.content.Count() - 1) and m.focus > m.last
                            m.first++
                            m.last++
                        end if
                        m.sounds.navSingle.Trigger(50)
                        m.Show()
                        msg = GetScreenMessage(m.focus, "focused")
                        exit while
                    end if
                end if
            else if index = m.codes.BUTTON_BACK_PRESSED and not m.ignoreBackKey
                m.sounds.navSingle.Trigger(50)
                m.Close()
                msg = GetScreenMessage(m.focus, "closed")
                exit while
            else if index = m.codes.BUTTON_SELECT_PRESSED
                m.sounds.select.Trigger(50)
                msg = GetScreenMessage(m.focus, "selected")
                exit while
            else if index < 100
                msg = GetScreenMessage(index, "remote")
                exit while
            end if
        end if
    end while
    return msg
End Function

Sub set_list_header(header as string)
    m.headerText = header
End Sub