import { privateAxios } from "./helper";
import { myAxios } from "./helper"; 
//create post function
export const createPost = (postData) => {
  //   console.log(postData);
  return privateAxios
    .post(`/user/${postData.userId}/category/${postData.categoryId}/posts`, postData)
    .then((response) => response.data);
};


//updatePost function
export const updatePost = (postData) => {
  console.log("In post-service.js => updatePost!!postData - "+postData);
  console.log("In post-service.js => updatePost!!postData.postId - "+postData.postId);
  return privateAxios
    // .put(`posts/${postData.postId}`, postData)
    .put(`posts/13`, postData)
    .then((response) => response.data);
};

//get all posts
// export function likePost(post,postId){
//   return privateAxios.put(`/like/{userId}/{postId}`, post).then((resp) => resp.data);
// }

export const loadAllPosts = (pageNumber, pageSize) => {
  return myAxios
    .get(
      `/posts?pageNumber=${pageNumber}&pageSize=${pageSize}&sortBy=addedDate&sortDir=desc`
    )
    .then((response) => response.data);
};

//load single post of given id
export const loadPost = (postId) => {
  console.log("In post-service => loadPost!!");
  return myAxios.get("posts/" + postId).then((reponse) => reponse.data);
};

// export const createComment = (comment, postId) => {
//   console.log("In post-service => createComment!!");
//   return privateAxios.post(`/posts/${postId}/comments`, comment);
// };
export const createComment = (comment, postId) => {
  return privateAxios.post(`/post/${postId}/comments`, comment);
};
//upload post banner image

export const uploadPostImage = (image, postId) => {
  let formData = new FormData();
  formData.append("image", image);
  return privateAxios
    .post(`/posts/image/upload/${postId}`, formData, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    })
    .then((response) => response.data);
};

//get cateory wise posts
export function loadPostCategoryWise(categoryId) {
  console.log("In loadPostCategoryWise!!");
  return privateAxios
    .get(`/api/category/`)
    .then((res) => res.data);
}

export function loadPostUserWise(userId) {
  console.log("In loadPostUserWise!!");
  console.log("In loadPostUserWise!! userId - "+userId);
  return privateAxios.get(`/user/2/posts`).then((res) => res.data);
}

//delete post
// export function deletePostService(postId) {
  
//   return privateAxios.delete(`/posts/${postId}`).then((res) => res.data);
// }
export function deletePostService(postId) {
  return privateAxios.delete(`/posts/${postId}`).then((res) => res.data);
}
//update post
export function updatePostService(post, postId) {
  console.log("In post-service updatePostService");
  console.log("In post-service updatePostService post = "+post);
  return privateAxios.put(`/posts/${postId}`, post).then((resp) => resp.data);
}

