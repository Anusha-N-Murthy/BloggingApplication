import { useState } from "react"
import { useEffect } from "react"
import { Card, CardBody, Form, Input, Label, Button, Container } from "reactstrap"
import { loadAllCategories } from "../services/category-service"
import JoditEditor from "jodit-react"
import { useRef } from "react"
import { updatePost as doUpdatedPost, uploadPostImage } from "../services/post-service"
import { getCurrentUserDetail } from "../auth"
import { toast } from "react-toastify"
const UpdatePost = () => {
    console.log("In UpdatePost.jsx => UpdatePost!!");
    const editor = useRef(null)
    // const [content,setContent] =useState('')
    const [categories, setCategories] = useState([])
    const [user, setUser] = useState(undefined)
    console.log("In UpdatePost.jsx => UpdatePost!!!!");
    const [post, setPost] = useState({
        title: '',
        content: '',
        categoryId: ''
    })
    console.log("In UpdatePost.jsx => UpdatePost!!!!@@@");
    const [image, setImage] = useState(null)


    // const config={
    //     placeholder:"Start typing...",

    // }

    useEffect(
        () => {
            console.log("In UpdatePost.jsx => UpdatePost.useEffect!!");
            console.log("In UpdatePost.jsx => UpdatePost.useEffect!! getCurrentUserDetail = "+getCurrentUserDetail());
            setUser(getCurrentUserDetail())
            console.log("In UpdatePost.jsx => UpdatePost.useEffect!! loadAllCategories = "+loadAllCategories());
            loadAllCategories().then((data) => {
                console.log("In UpdatePost.jsx => UpdatePost.useEffect!! loadAllCategories data = "+data);
                console.log(data)
                setCategories(data)
            }).catch(error => {
                console.log(error)
            })
        },
        []
    )

    //field changed function
    const fieldChanged = (event) => {
        // console.log(event)
        console.log("In UpdatePost.jsx => fieldChanged!!");
        setPost({ ...post, [event.target.name]: event.target.value })
    }

    const contentFieldChanaged = (data) => {

        setPost({ ...post, 'content': data })


    }


    //update post function
    const updatedPost = (event) => {

        event.preventDefault();

        // console.log(post)
        if (post.title.trim() === '') {
            toast.error("post  title is required !!")
            return;
        }

        if (post.content.trim() === '') {
            toast.error("post content is required !!")
            return
        }

        if (post.categoryId === '') {
            toast.error("select some category !!")
            return;
        }


        //submit the form one server
        post['userId'] = user.userId;
        doUpdatedPost(post).then(data => {


            uploadPostImage(image,data.postId).then(data=>{
                toast.success("Image Uploaded !!")
            }).catch(error=>{
                toast.error("Error in uploading image")
                console.log(error)
            })



            toast.success("Post updated !!")
            // console.log(post)
            setPost({
                title: '',
                content: '',
                categoryId: ''
            })
        })
        .catch((error) => {
            toast.error("Post not updated due to some error !!")
            // console.log(error)
        })

    }

    //handling file chagne event
    const handleFileChange=(event)=>{
        console.log(event.target.files[0])
        setImage(event.target.files[0])
    }


    return (
        <div className="wrapper">
            <Card className="shadow-sm  border-0 mt-2">
                <CardBody>
                    {/* {JSON.stringify(post)} */}
                    <h3>What going in your mind ?</h3>
                    <Form onSubmit={updatedPost}>
                        <div className="my-3">
                            <Label for="title" >Post title</Label>
                            <Input
                                type="text"
                                id="title"
                                placeholder="Enter here"
                                className="rounded-0"
                                name="title"
                                onChange={fieldChanged}
                            />
                        </div>

                        <div className="my-3">
                            <Label for="content" >Post Content</Label>
                            {/* <Input
                                type="textarea"
                                id="content"
                                placeholder="Enter here"
                                className="rounded-0"
                                style={{ height: '300px' }}
                            /> */}

                            <JoditEditor
                                ref={editor}
                                value={post.content}

                                onChange={(newContent) => contentFieldChanaged(newContent)}
                            />
                        </div>

                        {/* file field  */}

                        <div className="mt-3">
                            <Label for="image">Select Post banner</Label>
                            <Input id="image" type="file" onChange={handleFileChange} />
                        </div>




                         <div className="my-3"> 
                            <Label for="category" >Post Category</Label>
                           <Input type="select" id="category" placeholder="Enter here"  className="rounded-0"
                                name="categoryId" onChange={fieldChanged} defaultValue={0}>
                               <option disabled value={0} >--Select category--</option>
                                 { 
                                  categories.map((category) => ( 
                                    <option value={category.categoryId} key={category.categoryId}>
                                      {category.categoryTitle}
                                    </option>
                                )) 
                                } 
                            </Input>
                        </div>
 
                        <Container className="text-center">
                            <Button type="submit" className="rounded-0" color="primary">Update Post</Button>
                            <Button className="rounded-0 ms-2" color="danger">Reset Content</Button>
                        </Container> 
                    </Form>


                </CardBody>

            </Card>




        </div>
    )
}

export default UpdatePost
// import React from 'react'
// import { useState } from 'react'
// import { useEffect } from 'react'
// import { useContext } from 'react'
// import { useNavigate, useParams } from 'react-router-dom'
// import { toast } from 'react-toastify'
// import Base from '../components/Base'
// import userContext from '../context/userContext'
// import { loadPost, updatePost as doUpdatePost, updatePost } from '../services/post-service'
// import { loadAllCategories } from '../services/category-service'
// import { Card, CardBody, Form, Input, Label, Button, Container } from "reactstrap"
// import JoditEditor from "jodit-react"
// import { useRef } from "react"
// function UpdatePost() {

//     const editor = useRef(null)

//     const [categories, setCategories] = useState([])

//     const { blogId } = useParams()
//     const object = useContext(userContext)
//     const navigate = useNavigate()
//     const [post, setPost] = useState(null)

//     useEffect(() => {

//         loadAllCategories().then((data) => {
//             console.log(data)
//             setCategories(data)
//         }).catch(error => {
//             console.log(error)
//         })


//         //load the blog from database
//         loadPost(blogId).then(data => {
//             console.log(data)
//             setPost({ ...data, categoryId: data.category.categoryId })
//         })
//             .catch(error => {
//                 console.log(error);
//                 toast.error("error in loading the blog")
//             })
//     }, [])

//     useEffect(() => {
//         console.log("first")
//         if (post) {
//             // if (post.user.id != object.user.data.id) {
//                 if (post.user.userId != object.data.user.userId) {
//                 toast.error("This is not your post !!")
//                 navigate("/")
//             }

//         }

//     }, [post])

//     const handleChange = (event, fieldName) => {

//         setPost({
//             ...post,
//             [fieldName]: event.target.value
//         })

//     }

//     const updatePost = (event) => {
//         event.preventDefault()
//         console.log(post)
//         doUpdatePost({ ...post, category: { categoryId: post.categoryId } }, post.postId)
//             .then(res => {
//                 console.log(res)
//                 toast.success("Post updated")
//             })
//             .catch(error => {
//                 console.log(error);
//                 toast.error("Error in upading post")
//             })

//     }
//     const updateHtml = () => {
//         return (

//             <div className="wrapper">

//                 <Card className="shadow-sm  border-0 mt-2">
//                     <CardBody>

//                         <h3>Update post from here !!</h3>
//                         <Form onSubmit={updatePost} >
//                             <div className="my-3">
//                                 <Label for="title" >Post title</Label>
//                                 <Input
//                                     type="text"
//                                     id="title"
//                                     placeholder="Enter here"
//                                     className="rounded-0"
//                                     name="title"
//                                     value={post.title}
//                                     onChange={(event) => handleChange(event, 'title')}
//                                 />
//                             </div>

//                             <div className="my-3">
//                                 <Label for="content" >Post Content</Label>
//                                 {/* <Input
//                                 type="textarea"
//                                 id="content"
//                                 placeholder="Enter here"
//                                 className="rounded-0"
//                                 style={{ height: '300px' }}
//                             /> */}

//                                 <JoditEditor
//                                     ref={editor}
//                                     value={post.content}
//                                     onChange={newContent => setPost({ ...post, content: newContent })}
//                                 />
//                             </div>

//                             {/* file field  */}

//                             <div className="mt-3">
//                                 <Label for="image">Select Post banner</Label>
//                                 <Input id="image" type="file" onChange={''} />
//                             </div>




//                             <div className="my-3">
//                                 <Label for="category" >Post Category</Label>
//                                 <Input
//                                     type="select"
//                                     id="category"
//                                     placeholder="Enter here"
//                                     className="rounded-0"
//                                     name="categoryId"
//                                     onChange={(event) => handleChange(event, 'categoryId')}

//                                     value={post.categoryId}


//                                 >

//                                     <option disabled value={0} >--Select category--</option>

//                                     {

//                                         categories.map((category) => (
//                                             <option value={category.categoryId} key={category.categoryId}>
//                                                 {category.categoryTitle}
//                                             </option>
//                                         ))

//                                     }



//                                 </Input>
//                             </div>



//                             <Container className="text-center">
//                                 <Button type="submit" className="rounded-0" color="primary">Update  Post</Button>
//                                 <Button className="rounded-0 ms-2" color="danger">Reset Content</Button>
//                             </Container>


//                         </Form>


//                     </CardBody>

//                 </Card>




//             </div>
//         )
//     }


//     return (
//         <Base>
//             <Container>

//                 {post && updateHtml()}


//             </Container>
//         </Base>
//     )
// }

// export default updatePost
