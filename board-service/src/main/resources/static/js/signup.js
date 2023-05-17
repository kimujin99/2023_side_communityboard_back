//modal
const modal = document.querySelector('dialog');
const emailDuplicateCheckBtn = document.querySelector('#email-duplicate-check-btn');
const closeModalBtn = document.querySelector('#close_email_checking_modal');
const modalMsg = document.querySelector('#email-check-modal-msg');

//input
const emailInput = document.querySelector('#email');
const passwordInput = document.querySelector('#password');
const nicknameInput = document.querySelector('#nickname');
const profileInput = document.querySelector('#profile');
const profileNameInput = document.querySelector('#profileName');

//error
const passwordErr = document.querySelector('#passwordErr');
const passwordErrSpan = document.querySelector('#passwordErr span');
const emailErr = document.querySelector('#emailErr');
const emailErrSpan = document.querySelector('#emailErr span');
const nicknameErr = document.querySelector('#nicknameErr');
const nicknameErrSpan = document.querySelector('#nicknameErr span');
const profileErr = document.querySelector('#profileErr');
const profileErrSpan = document.querySelector('#profileErr span');

//checked
const passwordChecked = document.querySelector('#passwordChecked');
const emailChecked = document.querySelector('#emailChecked');
const nicknameChecked = document.querySelector('#nicknameChecked');

const submitSignupBtn = document.querySelector('#submit-signup-btn');

//체킹 변수
let emailValidChecking = false;
let emailDuplicateChecking = false;

let passwordChecking = false;
let nicknameChecking = false;
let profileChecking = true;

//email 공백 금지
emailInput.addEventListener('keyup', () => {
    const email = emailInput.value;
    emailInput.value = email.trim();
})

//email 변경 감지
emailInput.addEventListener('change', () => {
    emailValidChecking = false;
    emailDuplicateChecking = false;
    emailChecked.style.display = 'none';
})

//email 유효성 체크
function validateEmail() {
    const email = emailInput.value;
    const emailRegex = new RegExp(/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);

    emailChecked.style.display = 'none';

    if(email.trim() == '' || email == null) {
        emailValidChecking = false;
        emailErrSpan.innerText = 'ERROR : 이메일을 입력해주세요!';
        emailErr.style.display = 'block';
    } else if(!emailRegex.test(email)) {
        emailValidChecking = false;
        emailErrSpan.innerText = 'ERROR : 이메일 형식에 맞게 입력해주세요!';
        emailErr.style.display = 'block';
    } else {
        emailErr.style.display = 'none';
        emailValidChecking = true;
    }
}

//email 중복체크
emailDuplicateCheckBtn.addEventListener('click', ()=>{
    validateEmail();
    if(emailValidChecking) {
        //ajax
        emailDuplicateCheckAjax();
    }
})

//Modal 닫기
closeModalBtn.addEventListener('click', ()=>{
    modal.close();
})

//password 공백 금지
passwordInput.addEventListener('keyup', () => {
    const password = passwordInput.value;
    passwordInput.value = password.trim();
})

//password 변경 감지
passwordInput.addEventListener('change', () => {
    passwordChecking = false;
    passwordChecked.style.display = 'none';
})

//password 유효성 체크
function validatePassword() {
    const password = passwordInput.value;
    const passwordRegex = new RegExp('^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$');

    if(password.trim() == '' || password == null) {
        passwordChecking = false;
        passwordChecked.style.display = 'none';
        passwordErrSpan.innerText = 'ERROR : 비밀번호를 입력해주세요!';
        passwordErr.style.display = 'block';
    } else if(password.length < 6 || password.length > 16) {
        passwordChecking = false;
        passwordChecked.style.display = 'none';
        passwordErrSpan.innerText = 'ERROR : 비밀번호는 6~16자 사이입니다!';
        passwordErr.style.display = 'block';
    } else if(!passwordRegex.test(password)) {
        passwordChecking = false;
        passwordChecked.style.display = 'none';
        passwordErrSpan.innerText = 'ERROR : 비밀번호는 한 개 이상의 특수문자와 숫자를 포함합니다!';
        passwordErr.style.display = 'block';
    } else {
        passwordErr.style.display = 'none';
        passwordChecked.style.display = 'block';
        passwordChecking = true;
    }
}

//nickname 공백 금지
nicknameInput.addEventListener('keyup', () => {
    const nickname = nicknameInput.value;
    nicknameInput.value = nickname.trim();
})

//nickname 변경 감지
nicknameInput.addEventListener('change', () => {
    nicknameChecking = false;
    nicknameChecked.style.display = 'none';
})

//nickname 유효성 체크
function validateNickname() {
    const nickname = nicknameInput.value;

    nicknameChecked.style.display = 'none';

    if(nickname.trim() == '' || nickname == null) {
        nicknameChecking = false;
        nicknameErrSpan.innerText = 'ERROR : 닉네임을 입력해주세요!';
        nicknameErr.style.display = 'block';
    } else if(nickname.length < 2 || nickname.length > 16) {
        nicknameChecking = false;
        nicknameErrSpan.innerText = 'ERROR : 닉네임은 2~16자 사이입니다!';
        nicknameErr.style.display = 'block';
    } else {
        nicknameChecking = true;
        nicknameChecked.style.display = 'block';
        nicknameErr.style.display = 'none';
    }
}

//profile 형식 설정
var MaxSize = 10;
var validFileExts = ["PNG", "JPG", "JPEG"];

//profile 유효성 체크
function validateProfile() {
    const filePath = profileInput.value;
    const fileName = filePath.substring(filePath.lastIndexOf("\\") + 1);

    //파일명 인풋에 세팅
    profileNameInput.value = fileName;

    const fileSize = profileInput.files[0].size;
    const maxSize = 1024 * 1024 * MaxSize;

    const fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();

    console.log("filePath : " + filePath);
    console.log("fileName : " + fileName);
    console.log("fileSize : " + fileSize);
    console.log("fileExt : " + fileExt);

    if(maxSize < fileSize) {
        profileChecking = false;
        profileErrSpan.innerText = 'ERROR : 파일 용량은 10MB 까지만 업로드 가능합니다!';
        profileErr.style.display = 'block';
    } else if(!validFileExts.includes(fileExt)) {
        profileChecking = false;
        profileErrSpan.innerText = 'ERROR : 파일은 png, jpg, jpeg 형식만 업로드 가능합니다!';
        profileErr.style.display = 'block';
    } else {
        profileChecking = true;
        profileErr.style.display = 'none';
    }
}

profileInput.addEventListener('change', validateProfile);

submitSignupBtn.addEventListener('click', () =>{

    if(!emailDuplicateChecking) {
        modalMsg.innerText = '이메일 중복체크를 진행해주세요!'
        modal.showModal();
    } else {

        validatePassword();
        validateNickname();

        //ajax 통신
        if(passwordChecking && nicknameChecking && profileChecking) {
            signupAjax();
        }

    }

})

//ajax 통신
async function signupAjax() {
    const url = '/signup';
    const data = {
        userEmailId: emailInput.value,
        userPassword: passwordInput.value,
        userNickname: nicknameInput.value,
        userProfile: null,
    };

    try {

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        });

        //성공 시 새로고침
        if(response.status === 200) {
            window.location.href='/login';
        }

    } catch(err) {
        console.error(err);
    }
}

//ajax 통신
async function emailDuplicateCheckAjax() {
    const url = '/email-check.do';
    const data = {
        userEmailId: emailInput.value
    };

    try {

        const response = await fetch(url, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data)
        });

        //성공 시 새로고침
        if(response.status === 200) {
            const data = await response.json(); // response.json()으로 Promise 객체 추출
            emailDuplicateChecking = data.data; // data에서 boolean 결과 추출

            console.log("emailDuplicateChecking = " + emailDuplicateChecking);

            //결과 분기
            if(emailDuplicateChecking) {
                emailChecked.style.display = 'block';
            } else {
                modalMsg.innerText = '이미 사용중인 이메일입니다!'
                modal.showModal();
            }
        }

    } catch(err) {
        console.error(err);
    }
}