function sendCancelReserveServer (dateValue, timeFrom) {
    $.ajax({
        url: '/cancelReserve?dateValue=' + dateValue + "&timeFrom=" + timeFrom,
        dataType: 'json',
        context: this,
        success: function (data) {
            $dialogAnswer = $('#dialog_answer');
            $dialogAnswer.empty();

            var answer;
            switch (data.answer) {
                case 'OK':
                    answer = "Отмена прошла успешно";
                    break;
                default:
                    answer = "Возникла ошибка при отмене";
                    break;
            }

            $dialogAnswer.append($('<div />', {text: answer}))

            $dialogAnswer.dialog({
                modal: true,
                title: 'Результат',
                buttons: {
                    'OK': function () {
                        $(this).dialog('close');
                        updatePage();
                    },
                }
            });
        }
    });
}

function showDialogCancel(event) {
    $trElement = $(event.currentTarget).parent().parent().parent();
    $tdElement = $trElement.children();

    dateValue = $tdElement.eq(0).html();
    timeRange = $tdElement.eq(1).html();

    var firstPos = timeRange.indexOf(" ");
    var timeValue = timeRange.substr(0, firstPos);

    $dialogReserve = $('#dialog_reserve');
    $dialogReserve.empty();

    $dialogReserve.append($('<div />', {text: 'Дата: ' + dateValue, id: 'dateCancel'}));
    $dialogReserve.append($('<div />', {text: 'Время: ' + timeRange}));
    $dialogReserve.append($('<br />'));

    $dialogReserve.append($('<div />', {class: "warning",
    text: 'Предупреждение: если данный промежуток является частью времени бронирования, то будет отменен весь период бронирования'}));

    $dialogReserve.dialog({
        modal: true,
        title: 'Отмена ',
        buttons: {
            'Отменить': function () {
                sendCancelReserveServer(dateValue, timeValue);
                $(this).dialog('close');
            },
            'Закрыть': function () {
                $(this).dialog('close');
            },
        }
    });
}

function updatePage() {
    URL = $(".current_day").attr('href');
    new AjaxUpdatePage(URL).update();
}

$('body').on('click', '.cancel_button', function(event) {
    self.showDialogCancel(event);
    event.preventDefault();
});