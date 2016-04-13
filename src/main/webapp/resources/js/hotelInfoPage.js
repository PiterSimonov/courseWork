$(document).ready(function(){
    var inProgress = false;
    var startFrom = 5;
    $(window).scroll(function () {
        if($(window).scrollTop()+$(window).height() >= $(document).height() - 20 && !inProgress){
            var data = {};
            data['lastRoom']=startFrom;
            data['hotelId']=$("#id").val();
            $.ajax({
                url: "/roomNext",
                type: "POST",
                data: data,
                dataType: "json",
                beforeSend: function(){
                    inProgress=true;
                },
                success: function(roomList){
                    if (roomList.length>0){
                        $.each(roomList, function () {
                          addRoom(this);
                        });
                        inProgress=false;
                        startFrom+=5;
                    }
                }
            })
        }
    })
});
function addRoom(room){
    var tr = $('<tr class="room-table">');
    var imageTd = $('<td>');
    imageTd.append($('<img src="'+room.imageLink+'">'));
    var infoTd = $('<td>');
    infoTd.append($('<a href='+room.hotelId+'/roomDetails/'+room.id+'></a>').text("Room № "+room.number));
    infoTd.append($('<br/>'));
    infoTd.append("Type is "+room.type+"<br/>Places: "+room.seats+"<br/>Price: "+room.price+" &#36;");
    tr.append(imageTd);
    tr.append(infoTd);
    $('#table-rooms').append(tr);
}