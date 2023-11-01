import React from 'react'
import AddPost from '../../components/AddPost'
import UpdatePost from '../../components/UpdatePost'
import Base from '../../components/Base'
import { Container } from 'reactstrap'
import NewFeed from '../../components/NewFeed'
import { useState } from 'react'
import { useEffect } from 'react'
import { getCurrentUserDetail } from '../../auth'
import { deletePostService, loadPostUserWise } from '../../services/post-service'
import { toast } from 'react-toastify'
import Post from '../../components/Post'
const Userdashboard1 = () => {
  console.log("In Userdashboard1.jsx => Userdashboard1!!");
  const [user, setUser] = useState({})
  const [posts, setPosts] = useState([])
  useEffect(() => {
    console.log("In Userdashboard1.jsx => Userdashboard1.useEffect!!");
    console.log("In Userdashboard1.jsx => Userdashboard1.useEffect!! = "+getCurrentUserDetail());
    setUser(getCurrentUserDetail())
    loadPostData()

  }, [])

  function loadPostData() {
    console.log("In Userdashboard1.jsx => loadPostData!!");
    loadPostUserWise(getCurrentUserDetail().id).then(data => {
      console.log("In Userdashboard1.jsx => loadPostData.loadPostUserWise!!");
      console.log("In Userdashboard1.jsx => loadPostData.loadPostUserWise!! - "+data);
      console.log(data)
      setPosts([...data])
    })
      .catch(error => {
        console.log(error)
        toast.error("error in loading user posts")
      })
  }

  //function to delete post

  // function setDeletePost(post) {
  //   console.log("In Userdashboard1.jsx => deletePost!!");
  //   console.log("In Userdashboard1.jsx => deletePost!!post - "+post);
  //   console.log("In Userdashboard1.jsx => deletePost!!post.postId - "+post.postId);
  //   console.log("In Userdashboard1.jsx => deletePost!!post.title - "+post.title);
  //   console.log("In Userdashboard1.jsx => deletePost!!post.content - "+post.content);
  //   console.log("In Userdashboard1.jsx => deletePost!!post.imageName - "+post.imageName);
  //   console.log("In Userdashboard1.jsx => deletePost!!post.addedDate - "+post.addedDate);
  //   console.log("In Userdashboard1.jsx => deletePost!!post.category.categoryId - "+post.category.categoryId);
  //   console.log("In Userdashboard1.jsx => deletePost!!post.userId - "+post.userId);

    

    
    //going to delete post
   

    function deletePost(post) {
      //going to delete post
      console.log(post)
  
      deletePostService(post.postId).then(res => {
        console.log(res)
        toast.success("post is deleled..")
        let newPosts = posts.filter(p => p.postId != post.postId)
        setPosts([...newPosts])
  
      })
        .catch(error => {
          console.log(error)
          toast.error("error in deleting post")
        })
    }


  return (

    <Base>
      <Container>
      <h3 className='my-3'>Add Post</h3> 
      <AddPost />
      
    {/* 
      <hr></hr><br></br>
      <h3 className='my-3'>Update Post</h3> 
      <UpdatePost />
      <hr></hr><br></br>
  */}

        <h3 className='my-3'>Posts Count : ({posts.length})</h3>
   

        {/* {posts.map((post, index) => {
          return (
            <Post post={post} setDeletePost={setDeletePost} key={index} />
          )
        })} */}
         {posts.map((post, index) => {
          return (
            <Post post={post} deletePost={deletePost} key={index} />
          )
        })}

      </Container>
    </Base>

  )
}

export default Userdashboard1