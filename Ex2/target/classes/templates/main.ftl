<#import "parts/common.ftl" as c>

<@c.page>
    <h3>График использования ресурса:</h3>
    <div align=center>
        <div style="width:220px; border:1px solid #c0c0c0; padding:6px;">
            <table id="calendar"  border="0" cellspacing="0" cellpadding="1">
                <thead>
                <tr><td><b>‹</b><td colspan="5"><td><b>›</b>
                <tr><td>Пн<td>Вт<td>Ср<td>Чт<td>Пт<td>Сб<td>Вс
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
    <div>
        <table class="table_info">
            <tr>
                <th>Дата</th>
                <th>Время</th>
                <th>Кем занято</th>
                <th>Действия</th>
            </tr>
            <#list reserveDate as item>
                <tr>
                    <td>${item.date}</td>
                    <td>${item.timeFrom} - ${item.timeTo}</td>
                    <td class="userinfo">
                        <#if item.user??>
                            Username: ${item.user.username}<br>
                            Email: ${item.user.email}
                        </#if>
                    </td>
                    <td class="actioninfo">
                        <#if item.action??>
                            <#if item.action.allowedReserve>
                                <div class="action">
                                    <a href="#" class="reserve">Занять</a>
                                </div>
                            </#if>

                            <#if item.action.cancelReserve>
                                <div class="action">
                                    <a href="#" class="cancel_button">Отменить</a>
                                </div>
                            </#if>
                        </#if>
                    </td>
                </tr>
            </#list>
        </table>
    </div>
    <div id="dialog_reserve"></div>
    <div id="dialog_answer"></div>
    <script src="/js/calendar.js"></script>
    <script src="/js/main.js"></script>
    <script src="/js/dialogMain.js"></script>
    <script src="/js/dialogCancel.js"></script>
    <script src="/js/AjaxUpdatePage.js"></script>
</@c.page>