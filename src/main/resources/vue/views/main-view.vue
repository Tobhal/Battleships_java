<template id="main-view">
  <div>
    <h1>Lobby list:</h1>

    <ul class="lobby-list">
      <li v-for="lobby in lobbies">
        <div>
          <h2>{{lobby.name}}</h2>
          <p v-if="lobby.password == null"> Password </p>
          <p>Status: {{lobby.status}}</p>
          <p>ID: {{lobby.id}}</p>

          <p class="tooltip">
            Options
            <span class="tooltiptext">
              Board size: <br>
              <span class="tooltipList">X: {{lobby.options.boardX}} </span><br>
              <span class="tooltipList">Y: {{lobby.options.boardY}} </span><br>
              Boats in use:
              <span class="tooltipList" v-for="boat in lobby.options.boats">{{boat}} </span>
              Direction in use:
              <span class="tooltipList" v-for="direction in lobby.options.directions">{{direction}} </span>
            </span>
          </p>
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
<style >
  ul {
    list-style-type: none;
    margin: 0 0 0 1.25vw;
    padding: 0;
  }

  ul li h2 {
    margin-bottom: 0;
  }
  ul li p {
    margin: 0 0 0 1.25vw;
  }

  /* Tooltip */
  /* Tooltip container */
  .tooltip {
    position: relative;
    display: inline-block;
    border-bottom: 1px dotted black; /* If you want dots under the hoverable text */
  }

  /* Tooltip text */
  .tooltip .tooltiptext {
    visibility: hidden;
    width: 125px;
    background-color: slategray;
    color: #fff;
    text-align: left;
    border-radius: 6px;
    padding: 1em;

    /* Position the tooltip */
    position: absolute;
    z-index: 1;
    top: -20px;
    left: 110%;

    /* Fade in tooltip */
    opacity: 0;
    transition: opacity 0.3s;
  }

  .tooltipList {
    padding: 0 0 1vh 1vw;
  }

  /* Show the tooltip text when you mouse over the tooltip container */
  .tooltip:hover .tooltiptext {
    visibility: visible;
    opacity: 1;
  }


</style>