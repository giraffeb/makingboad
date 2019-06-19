// Google Oauth API
//google oauth2.0 api를 초기화합니다.

var auth2 = null;
var googleDebugMode = true;

//console debug 모드 출력 용
function myConsoleLog(message){
    if(googleDebugMode === true){
        console.log(message);
    }
}

//googla oauth api 초기화 -> 클라이언트(내 앱)으로 초기화하기.
function googleInit(){
    myConsoleLog("google Init Call");

    gapi.load('auth2', function(){
        //이 초기화가 oauth 과정에서 이미 리퀘스트 토큰을 받은 상태인 것
        auth2 = gapi.auth2.init({
            //내가 등록한 클라이언트 아이디
            client_id: '1041531983414-1etihq6otuj9kmo6bkkm0c10e9qae8ib.apps.googleusercontent.com'
            // ,scope: "profile"
        });

        //인증상태 변경 이벤트 시
        //TODO: google oauth signin state는 어떤거지? 어떤 관점으로 봐야 되지?
        auth2.isSignedIn.listen(function(response){
            myConsoleLog("signed in change");
            myConsoleLog(response);

        });
    });

    //구글 인증 버튼 그리기.
    renderButton();

}

//oauth button 성공 콜백함수
function onSuccess(googleUser) {
    myConsoleLog('Logged in as: ' + googleUser.getBasicProfile().getName());
    onSignIn();

}

//실패 콜백 함수.
function onFailure(error) {
    myConsoleLog(error);
}

//google oauth button 렌더링 옵션.
//권한 요청을 여기서 하자.
function renderButton() {
    gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 169,
        'height': 40,
        'longtitle': false,
        'theme': 'light',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });
}

//google sns login을 수행합니다.
//구글 버튼을 통해서 로그인 요청이 성공하면, 가져온 프로파일 아이디 토큰 값으로.
//이 함수로 내 웹의 로그인을 요청함.
function onSignIn(g) {

    myConsoleLog("call on Sign IN");
    let id_token = auth2.currentUser.get().getAuthResponse().id_token;
    myConsoleLog(id_token);

    document.getElementById("idtoken").value = id_token;
    document.getElementById("sns_type").value = "google";
    document.getElementById("snslogin").submit();
}

//로그아웃 기능.
//구글 oauth 로그아웃 처리할 필요 없이, 내 사이트에서만 처리하면 됨.
function googleSignOut(){
    myConsoleLog('singOut call');

    return new Promise(function(resolve, reject){
        auth2.currentUser.get().disconnect();
        resolve();
    })

}

//event등록
// var signOutLink = document.querySelector("a.nav-link.signout");
//
// if(signOutLink !== null){
//     signOutLink.addEventListener("click",function(event){
//         googleSignOut();
//     });
// }


