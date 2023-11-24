
function myOnLoading(){
    $('.preloader').show();
}
var urlDelete = window.location.pathname + '/delete';
var urlEdit = window.location.pathname + '/edit';
var urlSearch = window.location.pathname;
var urlChangeStatus = window.location.pathname + '/change-status';
var urlChangeGC = '/admin/manager-product/change-group-category';
var urlSearchProdToOrder = '/admin/manager-order/find-by-keyword';
var urlFindByGroup = '/admin/manager-order/find-by-group';
var urlFindByCategory = '/admin/manager-order/find-by-category';
var urlManagerOrder = '/admin/manager-order';
var urlAddOrder = '/admin/manager-order/add';

//delete order
function deleteOrder(id){
    alertify.dialog('confirm').set({
        transition:'zoom',
        title: 'Xác nhận xóa',
        message: 'Bạn có muốn xóa đơn hàng này không ?',
        'onok': function(){
            deleteWithAjax(id);
        }
    })
    .show();
}

//manager user
$('.btn-modal-app').on('click', function(){
    updateTitle('Thêm mới');
});
//global func with ajax
function deleteWithAjax(id){
    let data = {id : id}
    callAjax(urlDelete, 'DELETE', data);
}
function editWithAjax(id){
    let data = {id : id}
    callAjax(urlEdit, 'GET', data);
    $('#myModal').modal('show');
    updateTitle('Cập nhật');
}
function updateTitle(mode){
    $('.mode').text(mode);
}

function callAjax(url, method, data) {
    $.ajax({
      url: url,
      method: method,
      data: data
    }).then(function(response) {
       if(method != 'GET'){
           window.location.reload();
       }
       updateDataField(response);
//       console.log("response" + response)
    }).fail(function(error) {
       myToastr('error',  error.responseText);
    });
}
$.fn.changeStatus = function(id){
    let data = {
            id : id,
            statusChanged : $(this).prop("checked")
        }
    callAjax(urlChangeStatus, 'GET', data);
    myToastr('success',  'Cập nhật thành công!');
}

function updateDataField(data){
    for (var field in data) {
      if (data.hasOwnProperty(field)) {
        var value = data[field];
        // Check if the element is a Summernote editor by checking its ID
        if (field === "policy") {
            // Update the Summernote editor with the new content
            $('#policy').summernote('code', value);
        } else {
            // For other elements, you can use .val() to set their values
            $('[data-field="' + field + '"]').val(value);
        }
      }
    }
}
function callAjaxPromise(url, method, data) {
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

// change data list select when change
function updateCategoriesSelect(listCateGroupItems){
    $('#name_category').html('')
    listCateGroupItems.forEach( item => {
        $('#name_category').append(`
            <option value='${item.id}' >${item.name}</option>
        `);
    });
}

//search product to order
async function searchProduct(){
    let keyword = $('#inputSearchProd').val();
    let data = {keyword : keyword}
    let listProds = await callAjaxPromise(urlSearchProdToOrder, 'GET', data) || [];
    updateHtmlListProdSearch(listProds);
}

async function filterByCategory(){
    let id = $('#name_category').val();
    let data = {id : id}
    let listProds = await callAjaxPromise(urlFindByCategory, 'GET', data) || [];
    updateHtmlListProdSearch(listProds);
}

function addProductToOrder(productId, name, price){
    let item = {
        quantity : 1,
        productId : productId,
        name : name,
        price : price,
    }
    let listProductSelected = localStorage.getItem('listProductSelected') || [];
    if(listProductSelected.length > 0){ // have data in localStorage
        listProductSelected = JSON.parse(listProductSelected);
        let foundItem = listProductSelected.find( iz => parseInt(iz.productId) === parseInt(productId));
        if (foundItem !== undefined ) { // !== undefined )
            foundItem.quantity += 1;
        }else{
            listProductSelected.push(item);
        }
    }else{
        listProductSelected.push(item);
    }
    localStorage.setItem('listProductSelected', JSON.stringify(listProductSelected));
    updateHtmlListProdSelected(listProductSelected);
}
function deleteProdInListSelected(productId){
     let listProductSelected = localStorage.getItem('listProductSelected');
     listProductSelected = JSON.parse(listProductSelected);
     const newArray = listProductSelected.filter(item => parseInt(item.productId) !== parseInt(productId));
     localStorage.setItem('listProductSelected', JSON.stringify(newArray));
     updateHtmlListProdSelected(newArray);
}
function resetInfoOrder(){
    localStorage.removeItem('listProductSelected');
    $('#list_prod_selected_to_order').html('');
}


<!--    send order -->
$('#sendOrder').on('click', function sendOrder(){
    var listProductSelected = JSON.parse(localStorage.getItem('listProductSelected'));
    if(listProductSelected == null || listProductSelected.length <= 0){ // []
        alert('Vui lòng chọn sản phẩm!');
        return;
    }

    const urlParams = new URLSearchParams(window.location.href);
    var dataOrder = [];
    listProductSelected.map(product =>{
        let item = {
            productId : product.productId,
            quantity : product.quantity
        }
        dataOrder.push(item);
    });

    var dataToSend = {
            name:       $('#name_order').val(),
            address:    $('#address_order').val(),
            email:      $('#email_order').val(),
            phoneNumber:$('#phone_number').val(),
            note:       $('#note_order').val(),
            status:     $('#status_order').val(),
            idOrder :   $('#id_order_edit').val(),
            cartDtoList : dataOrder
    }
    if(dataOrder !== null && dataOrder.length > 0){
        $.ajax({
            url : urlAddOrder,
            method : 'POST',
            contentType: "application/json",
            dataType: "text",
            data : JSON.stringify(dataToSend)
        }).then(function(data){
            if(data == 'OK'){
                window.location.href = urlManagerOrder;
            }
        }).fail(function(error){
            console.log('error' + error)
        })
    }else{
        console.log('null');
    }
    localStorage.removeItem('listProductSelected');
});

/*add product to order*/
$('.btnAddProductToOrder').on('click', ()=>{
    event.preventDefault();
    filterByCategory();
})

// show price after discount (just for see)
$("#price").on("blur", function () {
    showPriceAfterDiscount();
});
$("#percent_discount").on("blur", function () {
    showPriceAfterDiscount();
});

function showPriceAfterDiscount() {
    var price = parseFloat($('#price').val());
    var percentDiscount = parseFloat($('#percent_discount').val());
    var discountedPrice = price * (1 - percentDiscount / 100);
    var roundedValue = roundToNearestThousand(discountedPrice);
    $('#price_after_discount').val(roundedValue.toString());
}

function roundToNearestThousand(value) {
    return Math.ceil(value / 1000) * 1000;
}


//update html list product search to order
function updateHtmlListProdSearch(listProds){
    $('#list_prod_order').html('')
    listProds.forEach( item =>{
        let nameProd = item.name.replace("'", "\\'");
        $('#list_prod_order').append(
        `
            <tr>
                <td>${item.name}</td>
                <td>${formatDecimal(item.price)}</td>
                <td>
                    <button onclick="addProductToOrder('${item.id}', '${nameProd}', '${item.price}')"
                    class="btn btn-primary btnSelectProd" type="button">Thêm</button>
                </td>
            </tr>
        `
        );
    })
}


//update html list product selected to order
function updateHtmlListProdSelected(listProds){
    if(listProds != null){
        $('#list_prod_selected_to_order').html('');
            listProds.forEach( (item, index) =>{
                $('#list_prod_selected_to_order').append(
                `
                    <tr>
                        <td>${index+1}</td>
                        <td>${item.name}</td>
                        <td>
                           ${item.quantity}
                        </td>
                        <td>${formatDecimal(item.price)}</td>
                        <td>
                            <button onclick="deleteProdInListSelected('${item.productId}')"
                            class="btn btn-danger" type="button">Xóa</button>
                        </td>
                    </tr>
                `
                );
            });
    }
}

//toastr
function myToastr(type, message){
     var delay = alertify.get('notifier', 'delay', 1);
     alertify.set('notifier','position', 'top-right');
     if(type == 'error'){
        alertify.error(message);
     }else{
        alertify.success(message);
     }
     alertify.set('notifier','delay', delay);

    /*Command: toastr[type](message)
    toastr.options = {
      "closeButton": true,
      "debug": true,
      "newestOnTop": true,
      "progressBar": false,
      "positionClass": "toast-top-right",
      "preventDuplicates": false,
      "onclick": null,
      "showDuration": "300",
      "hideDuration": "1000",
      "timeOut": "2000",
      "extendedTimeOut": "1000",
      "showEasing": "swing",
      "hideEasing": "linear",
      "showMethod": "fadeIn",
      "hideMethod": "fadeOut"
    }*/
}

function formatDecimal(num){
    return numbro(num).format({thousandSeparated: true});
}
