$(document).ready(function (){

    $("#more").on("click", function () {

        $.ajax({
            url : 'showMore',
            type: 'GET',
            success: function (data) {
                //console.log(data);

                $(".row").append(data);

                $(".show-more").fadeOut(200);
            },
            dataType: 'html'
        });
    });
});