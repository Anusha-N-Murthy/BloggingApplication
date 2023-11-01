import React from 'react'
import { useEffect } from 'react'
import { useContext } from 'react'
import { useState } from 'react'
import { Link } from 'react-router-dom'
import { Button, Card, CardBody, CardText } from 'reactstrap'
import { getCurrentUserDetail, isLoggedIn } from '../auth'
import userContext from '../context/userContext'
import { likePost } from '../services/post-service'
function Post({ post = { id: -1, title: "This is default post title", content: "This is default post content" }, deletePost }) {

    const userContextData = useContext(userContext)
    const [user, setUser] = useState(null)
    const [login, setLogin] = useState(null)
    const [liked, setLiked] = useState(false);
  const [likeCount, setLikeCount] = useState(0);
 
  const handleLikeClick = () => {
    if (liked) {
      setLikeCount(likeCount - 1);
    } else {
      setLikeCount(likeCount + 1);
    }
    setLiked(!liked);
  };
 
    useEffect(() => {
        setUser(getCurrentUserDetail())
        setLogin(isLoggedIn())
    }, [])
    return (


        <Card className='border-0 shadow-sm mt-3'>
            <CardBody>
                <h3>{post.title}</h3>
                <CardText dangerouslySetInnerHTML={{ __html: post.content.substring(0, 70) + "...." }}>

                </CardText>

                <div>
            
                <Link className='btn btn-secondary border-0' to={'/posts/' + post.postId}> Read More </Link>
                {/* {(post.postId>0 ? <Button onClick={(event) => setDeletePost(post)} color='danger' className="ms-2">Delete</Button> : '')} */}
                 {(post.postId>0 ? <Button onClick={(event) => deletePost(post)} color='danger' className="ms-2">Delete</Button> : '')}
               
                 {(post.postId>0 ? <Button tag={Link} to={`/user/dashboard2`} color='warning' className="ms-2">Update</Button>: '')}
                 {/* <Button  style={{marginLeft:"10px" ,width:"50px",color:"blue",backgroundColor:"gray"}}> <i className="fa fa-thumbs-up"></i></Button> */}
                 {/* <Button onClick={()=>likePost}><i className="fa fa-thumbs-up"></i> */}
        {/* {liked ? 'Unlike' : 'Like'} */}
{/* </Button> */}
<button onClick={handleLikeClick}><i className="fa fa-thumbs-up"></i>
        {liked ? 'Unlike' : 'Like'}
</button>

<span>{likeCount} Likes</span>
                </div>
            </CardBody>
        </Card>

    )
}

export default Post