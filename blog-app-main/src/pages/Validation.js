const Validation=(data)=>{
    let errors={}
    if(data.name){
        errors.name="Name Required";
    }
    else if(data.name.length<5){
        errors.name="Name must be  more than 5 character"
    }
    if(!data.passowrd){
        errors.name="password Required"
    }
    else if(data.password.length<8){
        errors.password="password must be  more than 8 character"
    }
    return errors
}
export default Validation;