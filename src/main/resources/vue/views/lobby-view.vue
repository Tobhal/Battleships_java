<template id="lobby-view">
  <div>
    <h1>{{lobby.name}}</h1>
    <p>ID: {{lobby.id}}</p>
    <p>Status: {{lobby.status}}</p>
    <p>Players: <span v-for="player in lobby.players">{{player.name}}, </span></p>
    <h2>Options:</h2>
    <p>Board x: {{lobby.options.boardX}} y: {{lobby.options.boardY}}</p>
    <p>Boats: <span v-for="boat in lobby.options.boats">{{boat}}, </span></p>
    <p>Directions: <span v-for="direction in lobby.options.directions">{{direction}}, </span></p>
  </div>
</template>
<script>
Vue.component("lobby-view", {
  template: "#lobby-view",
  data: () => ({
    lobby: [],
  }),
  created() {
    const lobbyID = this.$javalin.pathParams["lobbyID"];
    fetch(`/api/lobby/${lobbyID}`)
      .then(res => res.json())
      .then(res => this.lobby = res)
      .catch(() => alert(`Error fetching lobby`))
  }
});
</script>
<style>
  p {
    margin: 0;
    padding: 0;
  }
</style>