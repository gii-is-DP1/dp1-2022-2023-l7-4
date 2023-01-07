

  window.init = (gameId,selectedPositions,URL) => {
    function getGame(id) {
      return fetch('/api/game/'+id)
      .then(response => response.json());
    }
    getGame(gameId).then(game => {
    console.log("iniciando el mapa del juego "+gameId)
    console.log("posiciones destacadas: "+selectedPositions)
    console.log("URL: "+URL)
    selectedPositions= selectedPositions===undefined?[]:selectedPositions;
    const cities = game.cities;
    const paths = game.paths;
    console.log("game fetched",game)
    let nodes = []
    let links = []
    const network = {}



    const citySize = 200;
    const linkSize = citySize/1.5;
    const lineWidth = "0.5%";
    const lineColor = "lightgrey";
    const strokeColor = "#000"
    const circleRadius = 20;
    const positionRadius = citySize / 12;
    function parentWidth(elem) { return elem.parentElement.clientWidth; }
    function parentHeight(elem) { return elem.parentElement.clientHeight; }
    const width = parentWidth(document.getElementById('graph'));
    const height = parentWidth(document.getElementById('graph'));


    for (let index = 0; index < cities.length; index++) {
      const city = cities[index];
      city['nodeId'] = "city_" + city.id;
      city['nodeType'] = "city";
      nodes.push(city);

    }



    function findCoordsForPath(path, capacity, index) {

      let x1 = path.firstCity.x;
      let x2 = path.secondCity.x;
      let y1 = path.firstCity.y;
      let y2 = path.secondCity.y;
      const m = (x1 + x2) / 2
      const d = (x2 - x1)
      let x = x1 + (index * (x2 - x1)) / (capacity + 1)
      let y = y1 + (index * (y2 - y1)) / (capacity + 1)
      return { x: x, y: y }
    }
    for (let i = 0; i < paths.length; i++) {
      const path = paths[i];
      if(path.capacity==0){
        links.push({ source: "city_" + path.firstCity.id, target: "city_" + path.secondCity.id });
      }else if(path.capacity==1){
        links.push({ source: "city_" + path.firstCity.id, target: "pathPosition_" + path.positions[0].id  });
        links.push({ source: "pathPosition_" + path.positions[0].id , target: "city_" + path.secondCity.id });
        let alonePosition = path.positions[0];
          alonePosition['nodeId'] = "pathPosition_" + alonePosition.id;
          alonePosition['nodeType'] = "position";
          
        alonePosition.x = findCoordsForPath(path, path.capacity, 1).x;
        alonePosition.y = findCoordsForPath(path, path.capacity,  1).y;
        nodes.push(alonePosition);
      }else{

        for (let index = 0; index < path.capacity; index++) {
          let pathPosition = path.positions[index];
          pathPosition['nodeId'] = "pathPosition_" + pathPosition.id;
          pathPosition['nodeType'] = "position";
          
        pathPosition.x = findCoordsForPath(path, path.capacity, index + 1).x;
        pathPosition.y = findCoordsForPath(path, path.capacity, index + 1).y;
        nodes.push(pathPosition);
        
        // Asigna valores por defecto a todas las propiedades del objeto link
        let link = {
          source: "pathPosition_" + pathPosition.id,
          target: "pathPosition_" + pathPosition.id
        };
        // Modifica los valores de las propiedades en función de las distintas condiciones
        if (index === 0) {
          // Primer elemento
          link.source = "city_" + path.firstCity.id;
          links.push(link);
          
        } else if (index === path.capacity - 1) {
          // Último elemento
          link.target = "city_" + path.secondCity.id;
          links.push(link);
          links.push({ source: "pathPosition_" + (pathPosition.id - 1), target: "pathPosition_" + pathPosition.id })
          
        } else {
          // Elemento intermedio
          link.source = "pathPosition_" + (pathPosition.id - 1);
          links.push(link);

        }
      }
    }
    }
    console.log(network)
    network.nodes = nodes;
    network.links = links;
    //MAIN SVG, this stores everything inside
    var svg = d3.select("svg").append("g")
    
    //GENERATING LINKS
    var link_template = d3.linkVertical()
      .source(function (d) {
        let node = network.nodes.find(node => node.nodeId == d.source);
        return [node.x, node.y];
      })
      .target(function (d) {
        let node = network.nodes.find(node => node.nodeId == d.target);
        return [node.x, node.y];
      });

    var link = svg.append("g").attr("class", "links")
      .selectAll(".linePath")
      .data(network.links)
      .join("path")
      .attr("d", link_template)
      .attr("class", "linePath")
      .attr("id", function (d, i) { return "linePath_" + i; })
      .attr("stroke-width", lineWidth)
      .attr("stroke", lineColor)
      .attr("fill", "none")
      .classed("link", true);




    //GENERATING NODES
    var node = svg.append("g")
      .attr("class", "nodes")
      .selectAll("g")
      .data(network.nodes)
      .enter().append("g")
      .attr("id", d => d.nodeId).attr("class", d => d.nodeType + "_group")
      .call(d3.drag().on("drag", dragged));

      //POSITIONS GROUP
      
      var position = svg.selectAll("g.position_group").classed("selection", (position) => selectedPositions.indexOf(position.id) !== -1)
      .on("click",(event)=>{
        console.log(event.target.__data__.id)
        if(selectedPositions.indexOf(event.target.__data__.id) !== -1){
          document.getElementById('chosen-position').value = event.target.__data__.id
          document.getElementById('choose-position-form').submit();
        }
      } )
      var backCircle = position.append("circle")
      .attr("cx", 0)
      .attr("cy", 0)
      .attr("r", positionRadius)
      .attr("stroke", "white")
      .attr("stroke-width", "2")
      .attr("fill", d => d.color)
      
      
      position.filter(d=> d.occupied).append("svg:image")
      .attr("xlink:href", "/resources/images/troop_black.png")
      .attr("class", "img")
      .attr("width", positionRadius*1.5)
      .attr("height", positionRadius*1.5)
      .attr("x", -positionRadius/1.35)
      .attr("y", -positionRadius/1.35)
      
      //CITIES GROUP
      var cityG = svg.selectAll("g.city_group");
      
    var image = cityG.append("svg:image")
      .attr("xlink:href", d => d.startingCity ? "/resources/images/starting_city.png" : "/resources/images/white_city.png")
      .attr("class", "img")
      .attr("id", function (d) { "c" + d.id })
      .attr("x", -citySize / 2)
      .attr("y", -citySize / 2)
      .attr("width", citySize)
      .attr("height", citySize)
      




    var labels = cityG.append("text")
      .text(function (d) {
        return d.name;
      })
      .attr("text-anchor", "middle")
      .attr("font-size", citySize/10)
      .attr("fill", (d) => d.startingCity ? "white" : "black" )
      .attr('y', -citySize/5.4);

    var vpEndgameValue = cityG.append("text")
      .text(function (d) {
        return d.vpEndgameValue;
      })
      .attr('fill', (d) => d.startingCity ? "#fff" : "#000")
      .attr("text-anchor", "middle")
      .attr("font-size", "23")
      .attr('x', 1)
      .attr("y",  citySize/3.2);

    var div = svg.append("div")
      .attr("class", "tooltip")
      .style("opacity", 0);



      
      cities.forEach(city => {
        var spyPositions = city.spyPositions;
        var troopPositions = city.troopPositions;
        var selectedCity = svg.select("#city_"+city.id)
        
        for (let index = 0; index < spyPositions.length; index++) {
          const spyPosition = spyPositions[index];
          var spies = selectedCity.append("g").attr("class","spies").attr("id","spy_"+spyPosition.id)
          .classed("selection", selectedPositions.indexOf(spyPosition.id) !== -1)
          .on("click",()=>{
            console.log(spyPosition.id)
            if(selectedPositions.indexOf(spyPosition.id) !== -1){
              document.getElementById('chosen-position').value = spyPosition.id
              document.getElementById('choose-position-form').submit();
            }
          } )


        spies.append("circle")
      .attr("cx", ((-1)**(index)) * citySize/2.2)
      .attr("cy", (index<2) *  citySize/1.5 -citySize/2.9)
      .attr("r", positionRadius)
      .attr("stroke", spyPosition.occupied?"#ffff":"#fff1")
      .attr("stroke-width", "2")
      .attr("fill", ()=>{
        if(spyPosition.color===null) return (selectedPositions.indexOf(spyPosition.id) !== -1)?"#fff8":"#fff1";
        return spyPosition.color
      });

      spies.filter(()=>spyPosition.occupied)
      .append("svg:image")
      .attr("xlink:href", "/resources/images/spy_black.png")
      .attr("class", "img")
      .attr("width", positionRadius*1.5)
      .attr("height", positionRadius*1.5)
      .attr("x", ((-1)**(index)) * citySize/2.2-12)
      .attr("y", (index<2) *  citySize/1.5 -citySize/2.9 -14 )
    }

    for (let index = 0; index < troopPositions.length; index++) {
      const troopPosition = troopPositions[index];
      
      var troops = selectedCity.append("g").attr("class","troopsInCity")
      .classed("selection", () => selectedPositions.indexOf(troopPosition.id) !== -1)
      .on("click",()=>{
        console.log(troopPosition.id)
        if( selectedPositions.indexOf(troopPosition.id) !== -1){
          document.getElementById('chosen-position').value = troopPosition.id
          document.getElementById('choose-position-form').submit();
        }
        
        // window.location.assign(URL+"/"+troopPosition.id)
      } )
      
      troops.append("circle")
      .attr("cx", () => {
        if(city.capacity<3){
          let c = city.capacity;
          let space = positionRadius + 3;
          return ((index * 2 * space) - ((c - 1) * space));
      
        }else if(index<3){
            let c = 3;
            let space = positionRadius + 5;
            return ((index * 2 * space) - ((c - 1) * space));
          }else{
            let c = city.capacity-3;
            i = index -3;
            let space = positionRadius + 5;
            return ((i * 2 * space) - ((c - 1) * space));
          }
        
  })
  .attr("cy", ()=>{
    var offset =  citySize/40;
    if(city.capacity<=3)return offset;
    return ( ((-1)**(1+index<=3))*(positionRadius+3) ) + offset
    // return index<3?-positionRadius:+positionRadius;
  })
  .attr("r", positionRadius)
    .attr("stroke", () => city.startingCity ? "#fff" : "#000")
    .attr("stroke-width", "2")
    .attr("fill", troopPosition.color===null?"#fff0":troopPosition.color)
    
      
    
    troops.filter(()=>{return troopPosition.occupied})
    .classed("selection", selectedPositions.indexOf(troopPosition.id) !== -1)
    .on("click",(event)=>{
      console.log(troopPosition.id)
      if(selectedPositions.indexOf(troopPosition.id) !== -1){
        document.getElementById('chosen-position').value = troopPosition.id
        document.getElementById('choose-position-form').submit();
      }
    } );
    
    
    
    troops.filter(()=>{return troopPosition.occupied}).append("svg:image")
      .attr("xlink:href", "/resources/images/troop_black.png")
      .attr("class", "img")
      .attr("width", positionRadius*1.5)
      .attr("height", positionRadius*1.5)
  .attr("x", () => {
    if(city.capacity<3){
      let c = city.capacity;
      let space = positionRadius + 3;
      return ((index * 2 * space) - ((c - 1) * space)) -positionRadius/1.35;
  
    }else{
      if(index<3){
        let c = 3;
        let space = positionRadius + 5;
        return ((index * 2 * space) - ((c - 1) * space)) -positionRadius/1.35;
      }else{
        let c = city.capacity-3;
        i = index -3;
        let space = positionRadius + 5;
        return ((i * 2 * space) - ((c - 1) * space)) -positionRadius/1.35;
      }
    }
  })
  .attr("y", ()=>{
    var offset =  citySize/40;
    if(city.capacity<=3)return offset -positionRadius/1.3;
    return ( ((-1)**(1+index<=3))*(positionRadius+3) ) + offset -positionRadius/1.3
  })
    }



    function pulse() {
      svg.selectAll(".selection")
        .transition()
          .duration(500)
          .attr("opacity", 0.2)
          .delay(500)
        .transition()
          .duration(300)
          .attr("opacity", 1)
          .on("end", pulse)
          
    }
    
    pulse();

  
});







//SIMULATION

    const simulation = d3.forceSimulation(nodes)
      .force("charge", d3.forceManyBody().strength(d => {
        return d.nodeType === "city" ? -2000 : -2000;
      }))  
      .force("center", d3.forceCenter(width / 2, height / 2.5))
      .force("x", d3.forceX())
      .force("y", d3.forceY())


    simulation
      .nodes(network.nodes)
      .force("link", d3.forceLink(links).id(d => d.nodeId).distance(d => {
        if(d.source.nodeType === "position" && d.target.nodeType === "position"){
          return linkSize / 10;
        }else if(d.source.nodeType === "city" && d.target.nodeType === "city"){
          return linkSize*3;
        }else{
          return linkSize;
        }

      }).strength(0.7))
      .on("tick", render);

    function dragged(event, d) {
      d.x = event.x; d.y = event.y;
      d3.select(this).attr("cx", event.x).attr("cy", event.y);
      render();
    }

    function ticked() {
      render();
    }

    function render() {
      link.attr("d", d3.linkHorizontal()
        .x(function (d) { return d.x; })
        .y(function (d) { return d.y; }));

      node
        .attr("transform", function (d) {
          return "translate(" + d.x + "," + d.y + ")";
        });
    }
    let zoom = d3.zoom().on('zoom', handleZoom);

    function handleZoom(e) {
      d3.select('svg g')
        .attr('transform', e.transform);
    }
    function initZoom() {
      d3.select('svg')
        .call(zoom);
    }
    initZoom();

  });
  }
