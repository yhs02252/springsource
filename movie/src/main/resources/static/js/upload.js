// input file change -> [type=file] change event 처리

const fileInput = document.querySelector("[type='file']");

function showUploadImage(files) {
  const output = document.querySelector(".uploadResult ul");

  let tags = "";
  files.forEach((file) => {
    tags += `<li data-name="${file.fileName}" data-path="${file.folderPath}" data-uuid="${file.uuid}">`;
    tags += `<div>`;
    tags += `<a href=""><img src="/upload/display?fileName=${file.imageURL}" class="block"/></a>`;
    tags += `<span class="text-sm d-inline-block mx-1">${file.fileName}</span>`;
    tags += `<a href="${file.imageURL}" data-file=""><i class="fa-solid fa-xmark"></i></a>`;
    tags += `</div>`;
    tags += `</li>`;
  });
  output.insertAdjacentHTML("beforeend", tags);
}

fileInput.addEventListener("change", (e) => {
  // 서버로 input이 가지고 있는 File 전송
  const files = e.target.files;

  // 스크립트에서 form 작성하기
  let formData = new FormData();
  for (let index = 0; index < files.length; index++) {
    formData.append("uploadFiles", files[index]);
  }

  // formData 서버로 전송(ajax)
  fetch("/upload/upload", {
    method: "post",
    body: formData,
  })
    .then((response) => response.json())
    // .json 도 메소드이기때문에 ()를 꼭 붙여줘야한다
    .then((data) => {
      console.log("data 확인 : " + data);
      console.log(data);
      showUploadImage(data);
    });
});

// 작성 클릭 시
// form submit 중지
document.querySelector("#createForm").addEventListener("submit", (e) => {
  e.preventDefault();

  if (confirm("정말 이게 최선입니까?")) {
    // 첨부파일 정보 수집 : uploadResult li
    //   data-name, data-path, data-uuid
    const uploadResult = document.querySelectorAll(".uploadResult li");
    let result = "";
    uploadResult.forEach((obj, idx) => {
      // console.log(idx);
      // console.log(obj.dataset.name);
      // console.log(obj.dataset.path);
      // console.log(obj.dataset.uuid);
      result += `<input type="hidden" name="movieImageDTOs[${idx}].path" value="${obj.dataset.path}">`;
      result += `<input type="hidden" name="movieImageDTOs[${idx}].uuid" value="${obj.dataset.uuid}">`;
      result += `<input type="hidden" name="movieImageDTOs[${idx}].imgName" value="${obj.dataset.name}">`;
    });
    e.target.insertAdjacentHTML("beforeend", result);

    console.log(e.target.innerHTML);
    e.target.submit();
  }
});
