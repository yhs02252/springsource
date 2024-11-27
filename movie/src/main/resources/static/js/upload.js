// input file change -> [type=file] change event 처리

const fileInput = document.querySelector("[type='file']");

function showUploadImage(files) {
  const output = document.querySelector(".uploadResult ul");

  let tags = "";
  files.forEach((file) => {
    tags += `<li data-name="${file.fileName}" data-path="${file.folderPath}" data-uuid="${file.uuid}">`;
    tags += `<div>`;
    tags += `<a href=""><img src="/upload/display?fileName=${file.thumbImageURL}" class="block"/></a>`;
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

  if (confirm("해당 영화를 등록 하시겠습니까?")) {
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

document.querySelector(".uploadResult").addEventListener("click", (e) => {
  // a태그 기능 중지
  e.preventDefault();

  // 실제 x가 눌러진 태그 요소 찾기
  console.log("e.target(실제 이벤트 대상) : " + e.target);
  console.log("e.currentTarget(이벤트 대상의 부모) : " + e.currentTarget);

  // href 값 가져오기
  const element = e.target.closest("a");
  console.log(element);
  console.log(element.href);
  console.log(element.getAttribute("href"));

  // 이미지 삭제
  const removeDiv = e.target.closest("li");

  // 삭제할 이미지 경로 추출
  const filePath = element.getAttribute("href");

  let formData = new FormData();
  formData.append("filePath", filePath);

  fetch("/upload/remove", {
    method: "post",
    body: formData,
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("에러 발생");
      }
      return response.text();
    })
    .then((data) => {
      console.log(data);
      if (data) {
        removeDiv.remove();
      }
    });
});
