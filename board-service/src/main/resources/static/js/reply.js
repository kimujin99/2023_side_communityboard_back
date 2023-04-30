const replyContent = document.querySelector('#replyContent');
const replyList = document.querySelector('#replyList');
const replyChild = document.querySelector('#replyList div');
const addReplyBtn = document.querySelector('#add-reply-btn');
const replyErr = document.querySelector('#replyErr');

if(!replyChild) {
    replyList.style.display = "none";
}

//글자수 500자로 제한하기
replyContent.addEventListener('keyup', () =>{
    //replyContent 내용 담기
    const content = replyContent.value;

    //글자수 세기
    if(content.length == 0 || content == '') {
        document.querySelector(".textCount").innerText = "0";
    } else {
        document.querySelector(".textCount").innerText = content.length;
    }

    //글자수 제한
    if(content.length > 500) {
        //500자부터는 타이핑 불가
        content = content.substring(0, 500);
        document.querySelector(".textCount").innerText = 500;
    }
})

//댓글 작성 유효성 검사 -> ajax 통신
async function checkReplyAndAjax() {
    //replyContent 내용 담기
    const content = replyContent.value;
    //content 유효성 검사
    if(content == '' || content == null) {
        replyErr.style.display = 'block';
        return;
    } else {
        replyErr.style.display = 'none';
        await replyAjax();
    }
}

//댓글 ajax 통신
async function replyAjax() {
    const postingCode = document.querySelector('#postingCode').value;
    const url = '/boards/' + postingCode +'/reply';

    const replyWriteDTO = {
        postingCode: postingCode,
        userNickname: null,
        userProfile: null,
        replyContent: replyContent.value
    };

    try {

        const {ok, ...response} = await fetch(url, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(replyWriteDTO)
        });

        //성공 시 새로고침
        if(ok) {
            location.reload(true);
        }

    } catch(err) {
        console.error(err);
    }

//const postingCode = $("#postingCode").val();
//const MappingUrl = "/boards/" + postingCode + "/reply"
//const replyWriteDto = {
//    postingCode: postingCode,
//    userNickname: null,
//    userProfile: null,
//    replyContent: $("#replyContent").val(),
//};
//$.ajax({
//    url: MappingUrl,
//    type: "POST",
//    data: replyWriteDto,
//})
//.done(function (fragment) {
//     location.reload(true);
//
//});
}

// init
addReplyBtn.addEventListener('click', checkReplyAndAjax );