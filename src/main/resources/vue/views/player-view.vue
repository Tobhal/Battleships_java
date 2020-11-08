<template id="player-view">
  <div>
    <h1>{{player.name}}</h1>
    <p>Status: {{player.status}}</p>
    <p>Type: {{player.type}}</p>
    <p>Lobby: {{lobby.name}}</p>
    <p>Boats left: {{player.boatsAlive}}</p>
  </div>




</template>
<script>
Vue.component("player-view", {
  template: "#player-view",
  data: () => ({
    lobby: [],
    player: [],
  }),
  created() {
    const lobbyID = this.$javalin.pathParams["lobbyID"];
    const playerID = this.$javalin.pathParams["playerID"];
    fetch(`/api/lobby/${lobbyID}`)
      .then(res => res.json())
      .then(res => this.lobby = res)
      .catch(() => alert("Error fetching lobby"))
    fetch(`/api/lobby/${lobbyID}/player/${playerID}`)
      .then(res => res.json())
      .then(res => this.player = res)
      .catch(() => alert("Error fetching player"))
  },
  mounted() {
    let recaptchaScript = document.createElement('script')

    recaptchaScript.setAttribute('src','https://itstud.hiof.no/~tobiah/htdocs-new/script_battleships.js')

    document.head.appendChild(recaptchaScript)
  }
});



</script>
<style>
  p {
    margin: 0;
    padding: 0;
  }


</style>