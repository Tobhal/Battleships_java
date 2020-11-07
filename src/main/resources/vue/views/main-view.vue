<template id="main-view">
  <div>
    <h1>Main View</h1>

    <ul class="lobby-list">
      <li v-for="lobby in lobbies">
        <div>
          <h2>{{lobby.name}}</h2>
        </div>
      </li>
    </ul>

  </div>
</template>
<script>
Vue.component("main-view", {
  template: "#main-view",
  data: () => ({
    lobbies: [],
  }),
  created() {
    fetch("/api/lobbies")
      .then(res => res.json())
      .then(res => {
        this.lobbies = res;
      })
      .catch(() => alert("Error while fetching lobbies"));
  }
});
</script>
<style>

</style>