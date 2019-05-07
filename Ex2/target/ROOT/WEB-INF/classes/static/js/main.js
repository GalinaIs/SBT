function getInfo(event) {
    var URL = event.currentTarget.href;
    var self = this;

    $(".current_day").removeClass("current_day");
    $(event.currentTarget).addClass("current_day");

    new AjaxUpdatePage(URL).update();
}

$('body').on('click', '.see_info', function(event) {
    self.getInfo(event);
    event.preventDefault();
});