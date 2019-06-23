// facebook oauth api
function facebookInit() {
    console.log("fbAsyncInit call");

    FB.init({
        appId            : '2208086505911469',
        autoLogAppEvents : true,
        xfbml            : true,
        version          : 'v3.3'
    });

};

function facebookFecLoginStatus(){
    return new Promise(function(resolve, reject){
        FB.getLoginStatus(function (response){
            resolve(response);
        })
    });
}


function facebookLogin(){
    console.log("login complete");
    FB.login(function(response){
        if(response.status == "connected"){
            document.getElementById("sns_type").value = "facebook";
            document.getElementById("idtoken").value = response.authResponse.accessToken;
            document.getElementById("snslogin").submit();

        }
    }, {scope: "public_profile"});

/*
    FB.getLoginStatus(function(response){
        console.log("Hello status");
        console.log(response.status);


    })
*/

};

//facebook logout 필요없이 내 사이트에서만 로그아웃 처리하면 됨.
function facebookSignout(callback){
    console.log("facebook signout");

    facebookFecLoginStatus().then((res)=>{
        FB.logout((response)=>{
            resolve(response);
        });
    })

}