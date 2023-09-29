var urlAddCart = "/cart/add";
var urlUpdateCart = "/cart/update";
var urlDeleteCart = "/cart/delete";


$(window).on('load', function() {
    let currentItemSelected = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];

    if (currentItemSelected.length > 0) {
        $('input[name=chkCartItem]').each(function(index, item) {
            let checkExist = currentItemSelected.findIndex(cartItem => cartItem.productId === $(item).data('product-id'));
            if (checkExist !== -1) {
                $(item).prop('checked', true);
            }
        });
    }
    let totalPay = parseInt(JSON.parse(localStorage.getItem('totalPay'))) || 0;
    $('.totalCart').html(totalPay);
});


/* add cart */
async function addToCart(productId, quantity){
    let data  = {
        productId: productId,
        quantity: quantity
    }
    let amountCart = await callAjaxPromise('POST', urlAddCart, data);
    $('#checkout_items').text(amountCart);
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
    updateQuantityCartSelected(productId, cartItem.val());
    updateTotalPay(action ,productId);
}
async function deleteCart(productId){
    let data  = { productId: productId}
    let amountCart = await callAjaxPromise('DELETE', urlDeleteCart, data);
    $('#cartItem'+ productId).remove();
    $('#checkout_items').text(amountCart);
}
// choose product to -> pay
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
        currentItemSelected = currentItemSelected.filter(item => item.productId !== productId);
        totalPay -= (productPrice*quantity);
    }
    // Lưu danh sách cập nhật vào localStorage
    localStorage.setItem('listCartItemSelected', JSON.stringify(currentItemSelected));
    localStorage.setItem('totalPay', JSON.stringify(totalPay));
    $('.totalCart').html(totalPay);
});

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
        if ($(item).data('product-id') === productId && $(item).prop('checked')) { // Sử dụng $(item) để truy cập jQuery object
            priceProduct = $(item).data('product-price');
            let totalPay = parseInt(localStorage.getItem('totalPay')) || 0;

            if (action === 'decrease') {
                totalPay -= parseInt(priceProduct);
            } else {
                totalPay += parseInt(priceProduct);
            }
            $('.totalCart').html(totalPay);
            localStorage.setItem('totalPay', JSON.stringify(totalPay));
        }
    });
}

// go pay
//$('#order').on('click', function(){
//    let currentItemSelected = JSON.parse(localStorage.getItem('listCartItemSelected')) || [];
//    if(currentItemSelected.length !== 0){
//        $.ajax({
//            url: urlViewOrder,
//            method: 'POST',
//            contentType: "application/json",
//            data: localStorage.getItem('listCartItemSelected')
//        }).then(function(response) {
//
//        }).fail(function(error) {
//            console.log("error : " + error);
//        });
//    }else{
////         SwalAlertWarning('Bạn chưa chọn sản phẩm nào!');
//    }
//});


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