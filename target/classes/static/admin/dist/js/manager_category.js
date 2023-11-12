var infoItemIndex = 0;
var urlManagerProduct   = "/admin/manager-product";
var urlAddProduct       = "/admin/manager-product/add";

var productInfoList = $("#info_product-list .info_product-item");
var infoItemIndex = productInfoList.length -1 || 0;

$('.addInfo').on('click', (event)=>{
    event.preventDefault();
    infoItemIndex ++;
    var itemHtml = `
        <div class="row align-items-end mb-3 info_product-item" data-index="${infoItemIndex}">
            <div class="col">
                <label>Thông số</label>
                <input name="listKeys" type="text" class="form-control" placeholder="Nhập thông số ..." />
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


