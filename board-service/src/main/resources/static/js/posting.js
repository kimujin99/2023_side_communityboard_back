const nowPath = window.location.pathname.replace('/boards/', '');

const postingTitle = document.querySelector('#postingTitle');
const categoryCode = document.querySelector('#categoryCode');
const titleErr = document.querySelector('#titleErr');
const contentErr = document.querySelector('#contentErr');
const addPostingBtn = document.querySelector('#addPostingBtn');

//제목 글자수 100자로 제한하기
postingTitle.addEventListener('keyup', () =>{
    //제목 내용 담기
    const content = postingTitle.value;

    //글자수 제한
    if(content.length > 100) {
        //100자부터는 타이핑 불가
        content = content.substring(0, 100);
    }
})

//게시글 작성 유효성 검사 -> ajax 통신
async function checkPostingAndAjax() {
    //제목, 내용 담기
    const content = CKEDITOR.instances.editor4.getData();
    const title = postingTitle.value;

    //유효성 검사
    if (title.trim() == '' || title == null) {
        titleErr.style.display = 'block';
        contentErr.style.display = 'none';
        return;
    } else if (content.trim() == '' || content == null) {
        contentErr.style.display = 'block';
        titleErr.style.display = 'none';
        return;
    } else {
        contentErr.style.display = 'none';

        //ajax 통신
        if(nowPath === 'write') {
            writePosting();
        } else {
            editPosting();
        }
    }
}

//글 작성
async function writePosting() {
    const url = `/boards/write`;
    await postingAjax(url, null);
}

//글 수정
async function editPosting() {
    const postingCode = nowPath.replace('/edit', '');
    const url = `/boards/${postingCode}/edit`;
    await postingAjax(url, postingCode);
}

//ajax 통신
async function postingAjax(url, editPostingCode) {
    const data = {
        postingCode: editPostingCode,
        category: {
            categoryCode: categoryCode.value,
            categoryName: null,
        },
        postingTitle: postingTitle.value,
        editor4: CKEDITOR.instances.editor4.getData()
    };

    try {

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        });

        //성공
        if(response.status === 200) {
            const data = await response.json(); // response.json()으로 Promise 객체 추출
            const newPostingCode = data.data.postingCode; // data에서 postingCode 추출
            window.location.href='/boards/' + newPostingCode;
        }

    } catch(err) {
        console.error(err);
    }
}

// init
addPostingBtn.addEventListener('click', checkPostingAndAjax );
