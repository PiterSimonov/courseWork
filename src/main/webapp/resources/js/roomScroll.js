$(document).ready(function () {
    var inProgress = false;
    var startFrom = 5;
    $(window).scroll(function () {
        if ($(window).scrollTop() + $(window).height() >= $(document).height() - 20 && !inProgress) {
            var data = {};
            data["lastRoom"] = startFrom;
            $.ajax({
                url: '/search/roomsNext',
                type: 'POST',
                data: data,
                beforeSend: function () {
                    inProgress = true;
                }
            }).done(function (data) {
                if (data.length > 0) {
                    $.each(data, function (index, data) {
                        addRoom(data);
                    });
                    inProgress = false;
                    startFrom += 5;
                }
            });
        }
    });

    $('form#choice-room').on('change', 'input[type=checkbox]', function () {
        if ($('form#choice-room :checked').length <= 8) {
            if (this.checked) {
                var $submit = $('<input />', {type: 'submit', form: 'choice-room', value: 'Book'});
                $(this).after($submit);
            } else {
                $(this).next("input[type=submit]").remove();
                $('input[type=checkbox]').siblings('div').each(function(){
                    $(this).text("");
                })
            }
        } else {
            $(this).siblings('div').text("Sorry, but it's enough");
            $(this).attr('checked', false)
        }
    });

    $('form#choice-room').submit(function (e) {
        var userRole;
        $.ajax({
            url: "/get-user",
            type: "GET",
            async: false,
            success: function (responce) {
                userRole = responce;
            }
        });
        if (userRole == "HotelOwner" || userRole == 'CLIENT') {
            $(":submit").attr("disabled", true);
        } else {
            loadPopup();
            return false;
        }
    });

    var SORT_BY_PRICE_ASC = 0;
    var SORT_BY_PRICE_DESC = 1;
    var SORT_BY_SEATS_ASC = 2;
    var SORT_BY_SEATS_DESC = 3;

    $("#sortPrice").on("click", function () {
        startFrom = 5;
        var sortVal;
        if ($(this).val() === 'ByPriceAsc') {
            sortVal = SORT_BY_PRICE_ASC;
            $(this).val("ByPriceDesc");
        } else {
            sortVal = SORT_BY_PRICE_DESC;
            $(this).val("ByPriceAsc");
        }
        $.ajax({
            url: "/search/hotel/rooms/sort/" + sortVal,
            type: "GET",
            success: function (data) {
                $("#room-list").html("");
                $.each(data, function (index, data) {
                    addRoom(data);
                });
                inProgress = false;
            }
        });
    });

    $("#sortSeats").on("click", function () {
        startFrom = 5;
        var sortVal;
        if ($(this).val() === 'BySeatsAsc') {
            sortVal = SORT_BY_SEATS_ASC;
            $(this).val("BySeatsDesc");
        } else {
            sortVal = SORT_BY_SEATS_DESC;
            $(this).val("BySeatsAsc");
        }
        $.ajax({
            url: "/search/hotel/rooms/sort/" + sortVal,
            type: "GET",
            success: function (data) {
                $("#room-list").html("");
                $.each(data, function (index, data) {
                    addRoom(data);
                });
                inProgress = false;
            }
        });
    });
});

function addRoom(room) {
    var container = $('#room-list');
    var inputs = container.find('input[name=roomsIds]');
    var id = inputs.length + 1;
    var cbIndex = $('<input />', {type: 'checkbox', id: 'roomsIds' + id, name: "roomsIds", value: room.id});
    var cbIndex2 = $('<input />', {type: 'hidden', name: "_roomsIds", value: "on"});
    var row = $('<tr>');
    var tableData = $('<td>');
    var image;
    if (room.imageLink) {
        image = $('<img />', {src: room.imageLink});
    } else {
        image = $('<img />', {src: "/resources/images/rooms/noImage.jpg"});
    }
    tableData.append(image);
    row.append(tableData);
    var tableData2 = $('<td>');
    tableData2.append("Room № " + room.number + "<br/>Type is " + room.type + "<br/>" +
        "Places: " + room.seats + "<br/>Price: " + room.price + " &#36;");
    row.append(tableData2);
    var tableData3 = $('<td>');
    tableData3.append(cbIndex).append(cbIndex2).append($('<div>'));
    row.append(tableData3);
    container.append(row);
}