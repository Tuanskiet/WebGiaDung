$(document).ready(function() {
/*Urls*/
    var urlViewOrder    = "/get-data-order";
    var urlListProduct  = "/list-product";
    /*total order*/
    let total = parseInt(JSON.parse(localStorage.getItem('totalPay'))) || 0;
    $('.totalOrder').html(formatDecimal(total));

    /*show data order*/
    var dataOrder = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];
    if(dataOrder.length !== 0){
        $.ajax({
            url: urlViewOrder,
            method: 'POST',
            contentType: "application/json",
            data: localStorage.getItem('listCartItemSelected')
        }).then(function(response) {
            showHtmlOrder(response);
        }).fail(function(error) {
            console.log("error : " + error);
        });
    }
<!--    send order -->
    $('#sendOrder').on('click', function(){
        //validate
        const phonePattern = /^0\d{9,11}$/;
        let phone = $('#phoneNumber_order').val();
        if(!phonePattern.test(phone)){
            $(".error_phone").text('Số điện thoại không hợp lệ!');
            $(".error_phone").show();
            return;
        }

        var dataOrder = localStorage.getItem('listCartItemSelected');
                var dataToSend = {
                    name:       $('#name_order').val(),
                    address:    $('#address_order').val(),
                    email:      $('#email_order').val(),
                    phoneNumber:phone,
                    note:       $('#note_order').val(),
                    cartDtoList : JSON.parse(dataOrder)
                }

        if(dataOrder !== null){
            SwalAlertOrderSuccess('Đặt hàng thành công!');
            $.ajax({
                url : '/order',
                method : 'POST',
                contentType: "application/json",
                dataType: "text",
                data : JSON.stringify(dataToSend)
            }).then(function(data){
                if(data == 'OK'){
                    localStorage.removeItem('listCartItemSelected');
                    localStorage.removeItem('totalPay');
                }
            }).fail(function(error){
                console.error(error);
            })
        }else{
            alert("Please select product!")
        }

    });
    function showHtmlOrder(data){
          let html = data.map((item) => {
              return `
                    <div class="pay-info">
                        <div class="main__pay-text"> ${item.name} </div>
                        <div class="main__pay-amount">
                            ${item.quantity}
                        </div>
                        <div class="main__pay-price">
                            ${formatDecimal(item.price * item.quantity)} ₫
                        </div>
                    </div>
              `
          }).join('');
          $('.display-data-order').html(html);
          let total = parseInt(JSON.parse(localStorage.getItem('totalPay'))) || 0;
          $('.totalOrder').html(formatDecimal(total));
        };

    /*format number*/
    function formatDecimal(num){
        return numbro(num).format({thousandSeparated: true});
    }

    function SwalAlertOrderSuccess(message){
        alertify.alert('Thông báo', message).setting({
            'transition': 'zoom',
            'label': 'Tiếp tục mua hàng!',
            'onok': function() {
                window.location.href = urlListProduct;
        }
    }).show();
/*        Swal.fire({
            title: message,
            icon: "success",
            confirmButtonText: "Tiếp tục mua hàng!"
        }).then((result) => {
            if (result.isConfirmed) {
                 window.location.href = urlListProduct;
            }
        });*/
    }
});