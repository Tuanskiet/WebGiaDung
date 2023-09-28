$(document).ready(function() {
    var urlViewOrder = "/get-data-order";
    <!--    show data order -->
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
    }else{
//         SwalAlertWarning('Bạn chưa chọn sản phẩm nào!');
    }
//    if (dataOrder.length !== 0) {
//
//      let html = dataOrder.map((item) => {
//
//          return `
//                <div class="pay-info">
//                    <div class="main__pay-text"> ${item.nameProduct} </div>
//                    <div class="main__pay-amount">
//                        ${item.quantity}
//                    </div>
//                    <div class="main__pay-price">
//                        ${formatDecimal(item.priceProduct * item.quantity)} ₫
//                    </div>
//                </div>
//          `
//      }).join('');
//      $('.display-data-order').html(html);
//      let total = formatDecimal(totalPayment(data));
//      $('.totalOrder').text(total);
//    };

<!--    send order -->
    $('#sendOrder').on('click', function(){
        var dataOrder = localStorage.getItem('dataOrder');
        var dataToSend = {
            name:       $('#name_order').val(),
            address:    $('#address_order').val(),
            email:      $('#email_order').val(),
            phoneNumber:$('#phoneNumber_order').val(),
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
                    localStorage.removeItem('dataOrder');
                }
            }).fail(function(error){
                alert('error' + error)
            })
        }else{
            alert('null')
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
          $('.totalOrder').text(total);
        };

    function formatDecimal(num){
        return numbro(num).format({thousandSeparated: true});
    }
});