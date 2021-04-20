<template>
  <div class="home">
    <header>
      <h1>PXL Security Advanced</h1>
    </header>
    <div id="body">
      <div id="buttons-div">
        <div>
          <button v-on:click="fetchPublicEndpoint">Public Endpoint</button>
        </div>
        <div>
          <button v-on:click="fetchSecuredEndpoint('members')">Secure Members Endpoint</button>
        </div>
        <div>
          <button v-on:click="fetchSecuredEndpoint('poem')">Secure Poem Endpoint</button>
        </div>
        <div id="login-div" v-if="!$auth.loading">
          <button v-if="!$auth.isAuthenticated" @click="login">Log in</button>
          <button v-if="$auth.isAuthenticated" @click="logout">Log out</button>
        </div>
      </div>
      <div id="span-div">
        <div>
          <span>{{publicResponse}}</span>
        </div>
        <div>
          <span>{{members}}</span>
        </div>
        <div>
          <span>{{poem}}</span>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      publicResponse: "",
      poem: "",
      members: "",
    }
  },
  methods: {
    login() {
      this.$auth.loginWithRedirect();
    },
    logout() {
      this.$auth.logout({
        returnTo: window.location.origin
      });
    },
     fetchPublicEndpoint() {
      let url = 'https://localhost:8082/public';
      fetch(url, {
        method: "GET",
        headers: {
        }
      }).then(response => {
        return response.text();
      }).then(response => {
        this.publicResponse = response;
      })
    },
    async fetchSecuredEndpoint(endpoint) {
      const errorMessage = "U heeft niet de bevoegdheid om dit te zien!";
      let url = `https://localhost:8082/${endpoint}`;
      try {
        let accessToken = await this.$auth.getTokenSilently();
        fetch(url, {
          method: "GET",
          headers: {
            Authorization: `Bearer ${accessToken}`,
          }
        }).then(response => {
          if (response.status === 200) {
            return response.text();
          } else {
            throw new Error(errorMessage)
          }
        }).then(response => {
          this.correctOutput(response, endpoint)
        }).catch(error => {
          this.correctOutput(error.message, endpoint)
        })
      }catch (e) {
        this.correctOutput(errorMessage, endpoint)
      }
    },
    correctOutput(message, endpoint){
      if (endpoint === 'members') {
        this.members = message;
      }else {
        this.poem = message;
      }
    }
  }
}
</script>
<style >
  #body {
    display: flex;
    width: 900px;
    margin: auto;
    justify-content: center;
  }

  #buttons-div {
    width: 330px;
    display: flex;
    flex-direction: column;
  }

  #span-div{
    display: flex;
    flex-direction: column;
    width: 350px;
  }

  #span-div div{
    margin: 0.6em;
    text-align: left;
    height: 35px;
  }

  #buttons-div div {
    margin: 0.5em;
    float: left;
  }

  #buttons-div button {
    width: 320px;
    height: 35px;
    background-color: #42b983;
    color: white;
    border: none;
  }

  #login-div button {
    background-color: orange;
  }
</style>