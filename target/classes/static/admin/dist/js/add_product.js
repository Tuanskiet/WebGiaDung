var infoItemIndex = 0;
var urlManagerProduct   = "/admin/manager-product";
var urlAddProduct       = "/admin/manager-product/add";
var urlGetKeys       = "/admin/manager-category/get-key"; // key for product info

var productInfoList = $("#info_product-list .info_product-item");
var infoItemIndex = productInfoList.length -1 || 0;


async function getListInfoByCategory(){
    let id = $('#category').val();
    let data = {id : id}
    let listKeys = await callAjaxPromise(urlGetKeys, 'GET', data) || [];
    updateHtmlListKeys(listKeys);
}

function updateHtmlListKeys(data){
    $('#info_product-list').empty();
    data.forEach( (item,infoItemIndex)  => {
        var html = `
            <div class="row align-items-end mb-3 info_product-item" data-index="${infoItemIndex}">
                <div class="col">
                    <label>Thông số</label>
                    <input
                        value="${item}"
                        name="productInfo[${infoItemIndex}].key" type="text"
                        class="form-control keyInfo" placeholder="Nhập thông số ..." />
                </div>
                <div class="col">
                    <label>Giá trị</label>
                    <input name="productInfo[${infoItemIndex}].value" type="text"class="form-control valueInfo" placeholder="Nhập giá trị ..." />
                </div>
                <div class="col">
                    <button type="button" onclick="deleteInfoItem(${infoItemIndex})" class="btn btn-danger delete_product_info">
                        <i class="far fa-trash-alt"></i>
                    </button>
                </div>
            </div>
        `;
        $('#info_product-list').append(html);
    })
}

$('.addInfo').on('click', (event)=>{
    event.preventDefault();
    infoItemIndex ++;
    var itemHtml = `
        <div class="row align-items-end mb-3 info_product-item" data-index="${infoItemIndex}">
            <div class="col">
                <label>Thông số</label>
                <input name="productInfo[${infoItemIndex}].key" type="text" class="form-control keyInfo" placeholder="Nhập thông số ..." />
            </div>
            <div class="col">
                <label>Giá trị</label>
                <input name="productInfo[${infoItemIndex}].value" type="text"class="form-control valueInfo" placeholder="Nhập giá trị ..." />
            </div>
            <div class="col">
                <button type="button" onclick="deleteInfoItem(${infoItemIndex})" class="btn btn-danger delete_product_info">
                    <i class="far fa-trash-alt"></i>
                </button>
            </div>
        </div>
    `
    $('#info_product-list').append(itemHtml);
});

function deleteInfoItem(index) {
  $('.info_product-item').each(function(i, item) {
    if ($(item).data("index") === index) {
      $(item).remove();
    }
  });
}

$('#btnAddProduct').on('click', ()=>{
    var name = $('#name').val();
    var price = $('#price').val();
    var percentDiscount = $('#percent_discount').val();
    var categoryId = $('#category').val();
    var brandApp = $('#brand').val();
    var description = $('#description').val();
    var image = $('#main_img').val();
    var subImages = [];
    var listInfo = [];

    var objSubImages = $(".subImage");
    var objListInfo = $(".info_product-item");

    objSubImages.each( (index, item ) => {
        subImages.push($(item).val());
    });

    objListInfo.each( (index, item ) => {
        var infoItem = {
            "key" : $(item).find('.keyInfo').val(),
            "value" : $(item).find('.valueInfo').val()
        }
        listInfo.push(infoItem);
    });
    listInfo = listInfo.filter(item => item.key != null)

    //validate
    if(parseFloat(price) < 0 || price === ''){
        $('.add_product-error').text("Giá sản phẩm không hợp lệ!");
        $('.add_product-error').show();
        return;
    }else if(parseFloat(percentDiscount) < 0
            || parseFloat(percentDiscount) > 100
            || percentDiscount === ''){
        $('.add_product-error').text("Phần trăm giảm giá phải nằm trong khoảng từ 0 -> 100!");
        $('.add_product-error').show();
        return;
    }else{
        $('.add_product-error').hide();
    }

    var data = {
        name            : name,
        price           : price,
        percentDiscount : percentDiscount,
        categoryId      : categoryId,
        brandApp        : brandApp,
        description     : description,
        image           : image,
        subImages       : subImages,
        listProductInfo : listInfo
    }

    $.ajax({
        url    : urlAddProduct,
        method : "POST",
        contentType : "application/json",
        dataType: "text",
        data   : JSON.stringify(data)
    }).then((response)=>{
        if(response === 'CREATED'){
            window.location.href = urlManagerProduct
        }
    }).fail((error) => {
        $('.add_product-error').text(error.responseText);
        $('.add_product-error').show();
    })

});

$('.btnReset').on('click', () =>{
    window.location.reload();
})