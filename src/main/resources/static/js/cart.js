var urlAddCart = "/cart/add";
var urlUpdateCart = "/cart/update";
var urlDeleteCart = "/cart/delete";


$(window).on('load', function() {
   /* let currentItemSelected = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];
    if (currentItemSelected.length > 0) {

    }
    let totalPay = parseInt(JSON.parse(localStorage.getItem('totalPay'))) || 0;
    $('.totalCart').html(formatDecimal(totalPay));*/

    $('input[name=chkCartItem]').each(function(index, item) {
        $(item).prop('checked', false);
    });
    localStorage.removeItem('listCartItemSelected');
    localStorage.removeItem('totalPay');
});


$(document).ready(function() {
$('input[name=chkCartItem]').on('click', function() {
    let productId = $(this).data('product-id');
    let productPrice = $(this).data('product-price');
    let quantity = $('#cart'+productId).val();

    // Lấy danh sách mặt hàng đã chọn từ localStorage
    let currentItemSelected = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];
    let totalPay = parseInt(JSON.parse(localStorage.getItem('totalPay'))) || 0;
    if ($(this).prop('checked')) {
        // push cart item into localStorage
        let cartDtoItem = {
            productId: productId,
            quantity: quantity
        };
        currentItemSelected.push(cartDtoItem);
        // update total pay
        totalPay += (productPrice*quantity);
    }else{
        if(totalPay != 0){
            currentItemSelected = currentItemSelected.filter(item => item.productId !== productId);
            totalPay -= (productPrice*quantity);
        }
    }
    // Lưu danh sách cập nhật vào localStorage
    localStorage.setItem('listCartItemSelected', JSON.stringify(currentItemSelected));
    localStorage.setItem('totalPay', JSON.stringify(totalPay));
    $('.totalCart').html(formatDecimal(totalPay > 0 ? totalPay : 0));
    $('.type_order').html(totalPay > 2000000 ? 'Giao hàng miễn phí' : 'Người nhận trả ship');
});

});



/* add cart */
async function addToCart(productId, quantity){
    let data  = {
        productId: productId,
        quantity: quantity
    }
    try{
        let response = await callAjaxPromise('POST', urlAddCart, data);
        $('#checkout_items').text(response);
    }catch(error){
        window.location.hash = "my-Login";
        return false;
    }
    SwalAlertSuccess("Thêm thành công!");
    return true;
}

async function updateCart(action ,productId){
    let cartItem = $('#cart'+productId);
    if(action === 'decrease'){
        if(cartItem.val() == 1) return;
        cartItem.val( parseInt(cartItem.val()) - 1 );
    }else{
        cartItem.val( parseInt(cartItem.val()) + 1 );
    }
    let data  = {
        action: action,
        productId: productId
    }
    let amountCart = await callAjaxPromise('POST', urlUpdateCart, data);
    $('#checkout_items').text(amountCart);
    localStorage.removeItem('listCartItemSelected');
    localStorage.removeItem('totalPay');
    window.location.reload();
/*    updateQuantityCartSelected(productId, cartItem.val());
    updateTotalPay(action ,productId);
    window.location.reload();*/
}
async function deleteCart(productId){
    //update total cart
    updateTotalPay('delete' ,productId);

    let data  = { productId: productId}
    let amountCart = await callAjaxPromise('DELETE', urlDeleteCart, data);
    $('#cartItem'+ productId).remove();
    $('#checkout_items').text(amountCart);

}
// choose product to -> pay


function updateQuantityCartSelected(productId, quantity){
    let currentItemSelected = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];
    let itemExistIndex = currentItemSelected.findIndex(item => item.productId === productId);
    if (itemExistIndex !== -1) {
         let cartDtoItem = currentItemSelected[itemExistIndex];
         cartDtoItem.quantity = quantity;
         currentItemSelected[itemExistIndex] = cartDtoItem;
    }
    localStorage.setItem('listCartItemSelected', JSON.stringify(currentItemSelected));
}

function updateTotalPay(action, productId) {
    let listInput = $('input[name=chkCartItem]');
    let priceProduct = 0;
    listInput.each(function(index, item) {
        if ($(item).data('product-id') === productId && $(item).prop('checked')) {
            priceProduct = $(item).data('product-price');
            let totalPay = parseInt(localStorage.getItem('totalPay')) || 0;

            if (action === 'decrease') {
                totalPay -= parseInt(priceProduct);
            }else if (action === 'delete') {
                let quantity = $('#cart'+productId).val();
                totalPay -= (parseInt(priceProduct)*quantity);
                let currentItemSelected = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];
                currentItemSelected = currentItemSelected.filter(item => item.productId !== productId);
                localStorage.setItem('listCartItemSelected', JSON.stringify(currentItemSelected));
            }else {
                totalPay += parseInt(priceProduct);
            }
                $('.totalCart').html(formatDecimal(totalPay > 0 ? totalPay : 0));
            localStorage.setItem('totalPay', JSON.stringify(totalPay));
        }
    });
}


// go pay
$('#order').on('click', function(){
    let currentItemSelected = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];
    let totalPay = parseInt(localStorage.getItem('totalPay')) || 0;
    try{
        parseInt(totalPay);
    }catch(error){
         event.preventDefault();
    }
    if(currentItemSelected.length !== 0){
        // hanh dong thuc hien
    }else{
        event.preventDefault();
        SwalAlertWarning('Bạn chưa chọn sản phẩm nào!');
    }
});

/*format number*/
function formatDecimal(num){
    return numbro(num).format({thousandSeparated: true});
}

/*alert*/
function SwalAlertWarning(message){
    alertify.dialog('alert').set({
        transition:'zoom',
        title:'Thông báo',
        message: message
    }).show();
/*    Swal.fire({
        title: message,
        icon: 'warning',
        confirmButtonText: 'Đóng',
        timer: 2000,
    });*/
}
function SwalAlertSuccess(message){
    alertify.dialog('alert').set({
        transition:'zoom',
        title:'Thông báo',
        message: message
    }).show();

    /*Swal.fire({
        title: message,
        icon: "success",
        showConfirmButton: false,
        timer : 2000
    });*/

}
//call ajax
function callAjaxPromise(method, url, data) {
    return new Promise((resolve, reject) => {
      $.ajax({
        url: url,
        method: method,
        data: data
      }).then(function(response) {
        resolve(response);
      }).fail(function(error) {
        console.log("error : " + error);
        reject(error);
      });
    });
}
