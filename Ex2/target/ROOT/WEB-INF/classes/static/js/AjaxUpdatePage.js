class AjaxUpdatePage {

  constructor(URL) {
      this.URL = URL;
    }

    update() {
      $.ajax({
          url: this.URL,
          dataType: 'json',
          context: this,
          success: function (data) {
              var $tableInfoTd = $('.table_info td');
              var countTdInTr = 4;
              for (var i = 0; i < data.reserveDate.length; i++) {
                  $tableInfoTd.eq(i * countTdInTr).text(data.reserveDate[i].date);
                  $tableInfoTd.eq(i * countTdInTr + 1).text(data.reserveDate[i].timeFrom + " - " + data.reserveDate[i].timeTo);
                  if (data.reserveDate[i].user == null) {
                      $tableInfoTd.eq(i * countTdInTr + 2).html("");
                  } else {
                      $tableInfoTd.eq(i * countTdInTr + 2).html("Username: " + data.reserveDate[i].user.username + "<br>" + "Email: " +
                      data.reserveDate[i].user.email);
                  }

                  $tableInfoTd.eq(i * countTdInTr + 3).html("");
                  if (data.reserveDate[i].action.allowedReserve) {
                      $tableInfoTd.eq(i * countTdInTr + 3).append('<div class="action"><a href="#" class="reserve">Занять</a></div>');
                  }
                  if (data.reserveDate[i].action.cancelReserve) {
                      $tableInfoTd.eq(i * countTdInTr + 3).append('<div class="action"><a href="#" class="cancel_button">Отменить</a></div>');
                  }
              }
          }
      });
    }

}