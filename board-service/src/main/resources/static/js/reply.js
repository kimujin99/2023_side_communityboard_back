const textarea = document.getElementById("textarea_98")

textarea.addEventListener("keyup", (e)=>{
    //textarea 내용 담기
    let content = textarea.value;

    //글자수 세기
    if(content.length == 0 || content == '') {
        document.querySelector(".textCount").innerText = "0";
    } else {
        document.querySelector(".textCount").innerText = content.length;
    }

    //글자수 제한
    if(content.length > 500) {
        //500자부터는 타이핑 불가
        textarea.value = content.substring(0, 500);
        document.querySelector(".textCount").innerText = 500;
    }
});

const scrollInput = document.getElementById("scrollPosition");

//스크롤의 현재 위치 세팅
function setScrollPosition() {
    let scrollPosition = window.scrollY;
    scrollInput.value = scrollPosition
}