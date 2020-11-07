<template id="lobby-view">
  <div>
    <h1>Lobby!</h1>
    <p>{{id}}</p>
  </div>
</template>
<script>
Vue.component("lobby-view", {
  template: "#lobby-view",
  data: () => ({
    lobby: [],
    id,
  }),
  created() {
    id = this.$javalin.pathParams["lobbyID"];
    fetch('/api/lobby/${id}')
      .then(res => res.json())
      .then(res => this.lobby = res)
      .catch(() => alert(`Error fetching lobby`))

  }
});
</script>
<style>

</style>