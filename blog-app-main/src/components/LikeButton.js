import React, { useState } from 'react';
 
function LikeButton() {
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
 
  return (
<div>
<button onClick={handleLikeClick}>
        {liked ? 'Unlike' : 'Like'}
</button>
<span>{likeCount} Likes</span>
</div>
  );
}
 
export default LikeButton;