/**
 * Created by songchengzhong on 2017/1/12.
 */
$(function () {

    var usernameValid = function () {
        var $username = $("input[name='username']");
        if ($username.val().length < 4) {
            $username.next().html("用户名不能少于4位!");
            return false;
        } else if ($username.val().length > 16) {
            $username.next().html("用户名不能大于16位!");
            return false;
        } else {
            $username.next().empty();
            return true;
        }
    };
    var emailValid = function () {
        var $email = $("input[name='email']");
        var reg = /^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/;
        if (!reg.test($email.val())) {
            $email.next().html("请输入正确的email地址!");
            return false;
        } else {
            $email.next().empty();
            return true;
        }
    };
    var passwordValid = function () {
        var $password = $("input[name='password']");
        if ($password.val().length < 6) {
            $password.next().html("密码不能少于6位!");
            return false;
        } else if ($password.val().length > 32) {
            $password.next().html("密码不能大于32位!");
            return false;
        } else {
            $password.next().empty();
            return true;
        }
    };
    var passwordCheckValid = function () {
        var $password = $("input[name='password']");
        var $passwordCheck = $("input[name='password-check']");
        if ($passwordCheck.val() !== $password.val()) {
            $passwordCheck.next().html("两次输入密码不同!");
            return false;
        } else {
            $passwordCheck.next().empty();
            return true;
        }
    };

    //username
    $("input[name='username']").keyup(usernameValid);

    //email
    $("input[name='email']").keyup(emailValid);

    //password
    $("input[name='password']").keyup(passwordValid);

    //passwordCheck
    $("input[name='password-check']").keyup(passwordCheckValid);

    //提交按钮事件
    $("#submit-btn").click(function () {
        console.log(emailValid());
        console.log(usernameValid());
        console.log(passwordValid());
        console.log(passwordCheckValid());
        if (emailValid() && usernameValid() && passwordValid() && passwordCheckValid()) {
            return true;
        } else {
            console.log("error");
            return false;
        }
    });
});