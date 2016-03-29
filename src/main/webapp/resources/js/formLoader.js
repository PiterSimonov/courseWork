$(document).ready(function () {
    if (localStorage.getItem('form')) {
        //var roomCounter = 0;
        $('#left-panel').html(localStorage.getItem('form'));
        //var elements = $("#rooms :input[type='number']");
        //$.each(elements, function () {
        //    roomCounter++;
        //    $(this).on("change", function () {
        //        this.setAttribute("value", this.value);
        //    })
        //});
        //var buttons = $("#rooms :input[type='button']");
        //$.each(buttons, function () {
        //    $(this).on("click", function () {
        //        var el = this.previousElementSibling;
        //        this.parentElement.removeChild(el);
        //        this.parentElement.removeChild(this);
        //        roomCounter--;
        //        $("#addRoom").attr("disabled", false);
        //    })
        //});
    }
    ;
});
