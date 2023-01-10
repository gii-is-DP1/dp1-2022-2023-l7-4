<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<petclinic:layout pageName="login">
<div id="content">
    <h1>Iniciar sesión</h1>
    <form action="/login" method="POST">
        <div class="input-bar">
            <label for="name">Nombre de usuario</label>
            <input type="text" id="name" class="input" name="username">
            <box-icon name='user'></box-icon>
        </div>
        <div class="input-bar">
            <label for="password">Contraseña</label>
            <input type="password" id="password" class="input" name="password">
            <box-icon name='lock-alt' ></box-icon>
            <a class="glyphicon glyphicon-eye-open" style="text-decoration: none; cursor: pointer;" onclick="mostrarContrasena()"></a>
        </div>
        <div style="display: flex; align-items: center; justify-content: center; margin-bottom: 2%;">
            <p>¿No tienes ninguna cuenta? <a href="/user/new">Registrate</a></p>
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
    </form>
    
</div>
</petclinic:layout>

<style>
    * {
    outline: none;
}


#content {
    justify-content: center;
    margin-left: 30%;
    width: 450px;
    padding: 50px;
    box-shadow: 0 11px 17px 5px #5f4b8b;
    border-radius: 50px;
    text-decoration: none;
}

#content > h1 {
    font-size: 30px;
    background-position: center;
    justify-content: center;
    text-align: center;
}

.input-bar {
    justify-content: center;
    width: 350px;
    height: 60px;
    border: 2px solid #000;
    border-radius: 20px;
    margin: 30px 0;
    opacity: 0.5;
    transition: 200ms;
    font-weight: 600;
    position: relative;
}

.input-bar > label {
    position: absolute;
    font-size: 17px;
    text-transform: capitalize;
    top: 17px;
    left: 45px;
    transition: 200ms;
}

.input-bar > input {
    position: absolute;
    width: 100%;
    height: 100%;
    border: none;
    background: none;
    border-radius: 20px;
    box-sizing: border-box;
    padding: 20px 45px 10px;
    font-size: 18px;
}

.input-bar > box-icon {
    position: absolute;
    width: 26px;
    top: 10px;
    left: 10px;
}

.focus {
    opacity: 1;
}

.focus > label {
    top: 2px;
    font-size: 12px;
}

#btn {
    width: 350px;
    border: none;
    padding: 15px;
    color: #fff;
    background-color: blueviolet;
    font-size: 24px;
    border-radius: 30px;
    cursor: pointer;
}
.btn{
    width: 350px;
    font-size: 20px;
    border-radius: 30px;

}
.input-bar > a{
    left: 90%;
    margin-top: 20px;
    opacity: 1;
}


</style>

<script>
    const input = document.querySelectorAll('.input');

function inputFocus() {
    this.parentNode.classList.add('focus');
}

function inputBlur() {
    if(this.value == '' || this.value === null){
        this.parentNode.classList.remove('focus');
    }
}

input.forEach((e) => {
    e.addEventListener('focus', inputFocus);
    e.addEventListener('blur', inputBlur);
})

function mostrarContrasena(){
      var tipo = document.getElementById("password");
      if(tipo.type == "password"){
          tipo.type = "text";
      }else{
          tipo.type = "password";
      }
  }

</script>
