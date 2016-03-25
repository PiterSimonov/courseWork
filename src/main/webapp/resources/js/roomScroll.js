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
        if (this.checked) {
            var $submit = $('<input />', {type: 'submit', form: 'choice-room', value: 'Book'})
            $(this).after($submit);
        } else {
            $(this).next("input[type=submit]").remove();
        }
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
    tableData2.append("Room №" + room.number + "<br/>Type is" + room.type + "<br/>" +
        "Places: " + room.seats + "<br/>Price: " + room.price + " &#36;");
    row.append(tableData2);
    var tableData3 = $('<td>');
    tableData3.append(cbIndex).append(cbIndex2);
    row.append(tableData3);
    container.append(row);
}