const profileInput = document.querySelector('#profile');
const profileNameInput = document.querySelector('#profileName');
const profileErr = document.querySelector('#profileErr');
const profileErrSpan = document.querySelector('#profileErr span');
const profileImgPreview = document.querySelector('#profileImgPreview');
const deleteProfileBtn = document.querySelector('#deleteProfileBtn');

const originProfileSrc = profileImgPreview.src;
let profileChecking = true;

//profile 형식 설정
var MaxSize = 10;
var validFileExts = ["PNG", "JPG", "JPEG"];

//profile 유효성 체크
function validateProfile() {
    if(profileInput.files.length != 0) {
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
            resetFileInput();
            profileChecking = false;
            profileErrSpan.innerText = 'ERROR : 파일 용량은 10MB 까지만 업로드 가능합니다!';
            profileErr.style.display = 'block';
        } else if(!validFileExts.includes(fileExt)) {
            resetFileInput();
            profileChecking = false;
            profileErrSpan.innerText = 'ERROR : 파일은 png, jpg, jpeg 형식만 업로드 가능합니다!';
            profileErr.style.display = 'block';
        } else {
            readImg(profileInput);
            profileChecking = true;
            profileErr.style.display = 'none';
        }
    } else {
        resetFileInput();
        profileChecking = true;
    }

}

function readImg(input) {
    const reader = new FileReader();

    reader.onload = e => {
        profileImgPreview.src = e.target.result;
    }

    reader.readAsDataURL(input.files[0]);
}

profileInput.addEventListener('change', validateProfile);

deleteProfileBtn.addEventListener('click', () => {
    profileChecking = true;
    profileErr.style.display = 'none';
    resetFileInput();
})

function resetFileInput() {
    profileInput.value = '';
    profileNameInput.value = '';
    profileImgPreview.src = originProfileSrc;
}