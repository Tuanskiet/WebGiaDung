var urlAddCart = "/cart/add";
var urlUpdateCart = "/cart/update";
var urlDeleteCart = "/cart/delete";


/* add cart */
async function addToCart(productId, quantity){
    let data  = {
        productId: productId,
        quantity: quantity
    }
    let amountCart = await callAjaxPromise('POST', urlAddCart, data);
    alert(amountCart)
    //update amount number
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
    alert(amountCart)


    //update amount number
}
async function deleteCart(productId){
    let data  = { productId: productId}
    let amountCart = await callAjaxPromise('DELETE', urlDeleteCart, data);
    alert(amountCart)
    $('#cartItem'+ productId).remove();
    //update amount number
}

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
