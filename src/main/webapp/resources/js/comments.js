$(document).ready(function () {

    $('a.poplight[href^=#]').click(function () {

        $.ajax({
            url: "/search//comments/" + this.id,
            type: "GET",
            dataType: "json",
            success: function (data) {
                if (data.length == 0) {
                    $("#commentsWindow").append("<h1>No comments yet</h1>");
                } else {
                    data.forEach(function (i) {
                        var user = i.user;

                        $("#commentsWindow").append("<hr><span>" + user.firstName + " " + user.lastName + "</span><span><strong> " + i.rating + "</strong> / 5</span><div>" +
                            " <blockquote>" + i.comment + "</blockquote></div><hr>");
                    })
                }
            }
        });

        var popID = $(this).attr('rel'); //получаем имя окна, важно не забывать, при добавлении новых, менять имя в атрибуте rel ссылки

        //Добавляем к окну кнопку закрытия
        $('#' + popID).fadeIn().css({
            'width': 700,
            'max-height': 400
        }).prepend('<a href="#" title="Закрыть" class="close"></a>');

        //Определяем маржу(запас) для выравнивания по центру (по вертикали и горизонтали) - мы добавляем 80 к высоте / ширине с учетом отступов + ширина рамки определённые в css
        var popMargLeft = ($('#' + popID).width() + 80) / 2;

        //Устанавливаем величину отступа
        $('#' + popID).css({
            'margin-top': -250,
            'margin-left': -popMargLeft
        });

        //Добавляем полупрозрачный фон затемнения
        $('body').append('<div id="fade"></div>'); //div контейнер будет прописан перед тегом </body>.
        $('#fade').css({'filter': 'alpha(opacity=80)'}).fadeIn(); //полупрозрачность слоя, фильтр для тупого IE

        return false;
    });
//Закрываем окно и фон затемнения
    $(document).on('click', 'a.close, #fade', function () { //закрытие по клику вне окна, т.е. по фону...
        $('#fade , .popup_block').fadeOut(function () {
            $('#fade, a.close').remove();  //плавно исчезают
        });
        $("#commentsWindow").html("");
        return false;
    });

});




