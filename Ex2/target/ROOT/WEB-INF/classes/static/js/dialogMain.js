function createSelect (idDivWithSelect, arrayValue, value) {
    var $dialogSelect = $('#' + idDivWithSelect);
    for (var ind = 0; ind < arrayValue.length; ind++) {
        if (arrayValue[ind] == value) {
            $dialogSelect.append($('<option />', {
                value: arrayValue[ind],
                text: arrayValue[ind],
                selected: true
            }));
        } else {
            $dialogSelect.append($('<option />', {
                value: arrayValue[ind],
                text: arrayValue[ind]
            }));
        }
    }
}

function sendReserveServer (dateValue, timeFrom, duration) {
    var self = this;

    $.ajax({
        url: 'http://localhost:8080/reserve?dateValue=' + dateValue + "&timeFrom=" + timeFrom + "&duration=" + duration,
        dataType: 'json',
        context: this,
        success: function (data) {
            $dialogAnswer = $('#dialog_answer');
            $dialogAnswer.empty();

            var answer;
            switch (data.answer) {
                case 'OK':
                    answer = "Резервирование прошло успешно";
                    break;
                case 'Cancel':
                    answer = "Резервирование невозможно, т.к. идет пересечение с уже забронированным временем";
                    break;
                case 'TooLongDuration':
                    answer = "Невозможно забронировать время после 18 часов";
                    break;
                case 'LastDate':
                    answer = "Невозможно забронировать время в прошлом";
                    break;
                default:
                    answer = "Возникла ошибка при бронировании";
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

function showDialog(event) {
    $trElement = $(event.currentTarget).parent().parent().parent();
    $tdElement = $trElement.children();

    $timeRange = $tdElement.eq(1).html();
    var firstPos = $timeRange.indexOf(" ");
    var timeValue = $timeRange.substr(0, firstPos);

    $dialogReserve = $('#dialog_reserve');
    $dialogReserve.empty();

    $dialogReserve.append($('<label />', {text: 'Дата: '}))
    .append($('<input />',{name: "date", value: $tdElement.eq(0).html(), id: "datep"}));

    $dialogReserve.append($('<label />', {text: 'Время с: '}))
    .append($('<select />', {name: "timeTo", id: "select_time", autofocus: true}));
    createSelect("select_time", ["09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"], timeValue);

    $dialogReserve.append($('<label />', {text: 'Продолжительность: ', id: "duration"})).
    append($('<select />', {name: "duration", id: "select_duration"}));
    createSelect("select_duration", [1, 2, 3, 4, 5], 1);

    $.datepicker.setDefaults($.datepicker.regional['ru']);
    $('#datep').datepicker({
        minDate: "+0",
	    dateFormat: "yy-mm-dd",
	});

    $dialogReserve.dialog({
        modal: true,
        title: 'Введите дату и время ',
        buttons: {
            'Забронировать': function () {
                sendReserveServer($('#datep').val(), $('#select_time').val(), $('#select_duration').val());
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

$('body').on('click', '.reserve', function(event) {
    self.showDialog(event);
    event.preventDefault();
});