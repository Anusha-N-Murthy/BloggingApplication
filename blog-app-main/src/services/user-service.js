import { myAxios } from "./helper";

export const signUp = (user) => {
  console.log("In user-service => signUp!!");
  return myAxios.post("/auth/register", user).then((response) => response.data);
};

export const loginUser = (loginDetail) => {
  console.log("In user-service => loginUser!!");
  return myAxios
    .post("/auth/login", loginDetail)
    .then((response) => response.data);
};

export const getUser = (userId) => {
  console.log("In user-service => getUser!!");
  return myAxios.get(`/users/${userId}`).then((resp) => resp.data);
};
