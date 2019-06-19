//outh 관리

async function oauthLogout(){
    console.log("oauthLogout Call");

    await googleSignOut();
    // await facebookSignout();
    document.getElementById("logout").submit();

}