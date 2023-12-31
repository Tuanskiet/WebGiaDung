var infoItemIndex = 0;
var urlManagerProduct   = "/admin/manager-product";
var urlAddProduct       = "/admin/manager-product/add";
var productInfoList = $("#info_product-list .info_product-item");

$('.addInfo').on('click', (event)=>{
    event.preventDefault();
    infoItemIndex ++;
    var itemHtml = `
        <div class="row align-items-end mb-3 info_product-item" data-index="${infoItemIndex}">
            <div class="col">
                <label>Thông số</label>
                <input name="productInfo[${infoItemIndex}].key" type="text" name="keyInfo" class="form-control" placeholder="Nhập thông số ..." />
            </div>
            <div class="col">
                <label>Giá trị</label>
                <input name="productInfo[${infoItemIndex}].value" type="text" name="valueInfo" class="form-control" placeholder="Nhập giá trị ..." />
            </div>
            <div class="col">
                <button onclick="deleteInfoItem(${infoItemIndex})" class="btn btn-danger delete_product_info">
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
    var brandId = $('#brand').val();
    var description = $('#description').val();
    var image = $('#main_img').val();
    var subImages = [];
    var listInfo = [];

    var objSubImages = $("input[name='subImage'");
    var objListInfo = $(".info_product-item");

    objSubImages.each( (index, item ) => {
        subImages.push($(item).val());
    });

    objListInfo.each( (index, item ) => {
        var infoItem = {
            "key" : $(item).find('[name="keyInfo"]').val(),
            "value" : $(item).find('[name="valueInfo"]').val()
        }
        listInfo.push(infoItem);
    });


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
        brandAppId      : brandId,
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