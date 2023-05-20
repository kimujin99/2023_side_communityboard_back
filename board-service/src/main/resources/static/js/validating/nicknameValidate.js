const nicknameInput = document.querySelector('#nickname');
const nicknameErr = document.querySelector('#nicknameErr');
const nicknameErrSpan = document.querySelector('#nicknameErr span');
const nicknameChecked = document.querySelector('#nicknameChecked');

let nicknameChecking = false;

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