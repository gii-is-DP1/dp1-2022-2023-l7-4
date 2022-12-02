<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://d3js.org/d3.v7.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/map.js"></script>
<style>

</style>
<petclinic:layout pageName="positions">
  <div >
  <div id = "aa" ></div>
  <svg id="graph" style="background-color: rgb(39, 11, 65)"></svg>
  </div>
  <script>

    let cities=[]
    let y=[]
  </script>
<c:forEach items="${cities}" var="city">
  <c:out value="${city.name}"/>
  <script>
    cities.push({ 
      id: parseInt("${city.id}")  ,
      name: "${city.name}",
      zone: parseInt("${city.zone}"),
      vp: parseInt("${city.vpEndgameValue}"),
      isStartingCity: "true" =="${city.isStartingCity}",
      capacity: parseInt("${city.capacity}"),
      x: 50,
      y: 50
    
    })
    
    
    </script>
</c:forEach>

<c:forEach items="${paths}" var="path">
  <script>
    y.push({ 
      id: parseInt("${path.id}")  ,
      pathName: "${path}",
      target: parseInt("${path.firstCity.id}"),
      source: parseInt("${path.secondCity.id}"),
      capacity: parseInt("${path.capacity}"),
      type: "html"
    
    })
    
    
    </script>
</c:forEach>


<script>
  console.log(y)
  let html = "<div><h2>Mapa Interactivo</h2></div>";

  document.getElementById("aa").innerHTML = html;
</script>



<script>

(function (d3$1) {
  'use strict';

  const network = {}


  network.nodes = cities;
  network.links = y;
  const positionColor =null;
  const lineWidth = 4;
  const lineColor = "lightgrey";
  const strokeColor = "lightgrey";
  const circleRadius = 20;
  const forceStrength = 0.;
  const linkStrength = 0.;
  const positionRadius = 10;
  function parentWidth(elem) {return elem.parentElement.clientWidth;}
  function parentHeight(elem) {return elem.parentElement.clientHeight;}
  const width = parentWidth(document.getElementById('graph'));
  // const height = parentHeight(document.getElementById('graph'));
  const height = 500;

  //svg es el tag <svg> de html a rellenar
  var svg = d3$1.select("svg")
      svg.attr("width",width)
      svg.attr("height",height);

  //centrado en el svg
  network.nodes.forEach(node => {
    node.x = (node.x * width) / 100;
    node.y = (node.y * height) / 100;
  });
    
  //calula direccion del camino respecto a los ids
  network.links.forEach(link => {
    //objetos de entrada y salida
    let source = network.nodes.find(node => node.id == link.source);
    let target = network.nodes.find(node => node.id == link.target);

    if (source.x < target.x) {
    	link['dir'] = 'forward';
    } else {
    	link['dir'] = 'reverse';
      let temp = link.source;
      link.source = link.target;
      link.target = temp;
    }
  });


  var link_template = d3.linkVertical()
      .source(function(d) {
        let node = network.nodes.find(node => node.id == d.source);
  	    return [node.x, node.y];
      })
      .target(function(d) {
        let node = network.nodes.find(node => node.id == d.target);
        return [node.x, node.y];
      });





  var link = svg.selectAll(".linePath")
      .data(network.links)
      .join("path")
      .attr("d", link_template)
  		.attr("class", "linePath")
  		.attr("id", function(d, i) { return "linePath_"+i; })
  		.attr("stroke-width", lineWidth)
      .attr("stroke", lineColor)
      .attr("fill", "none")
  		.classed("link", true); 


  var linkText = svg.selectAll(".lineText")
      .data(network.links)
      .enter().append("text")
  		.attr("class", "lineText")
      .attr("fill", lineColor)
  		.attr('dy', -4)
      .append("textPath")
  		.attr("xlink:href", function(d,i) { return "#linePath_"+i; })
      .style("text-anchor", d => d.dir == 'forward' ? "end" : "start" )
  		.attr("startOffset", d => d.dir == 'forward' ? "85%" : "45px" )
  		.text(function(d) {return ""+d.pathName; });//+d.pathName

  //add mouse over chart
  var tooltip = d3.select("body")
      .append("div")
      .style("position", "absolute")
      .style("z-index", "10")
      .style("visibility", "hidden")
      .style("background", "#fff")
      .text("a simple tooltip");

  var node = svg.append("g")
      	.attr("class", "nodes")
      .selectAll("g")
      .data(network.nodes)
      .enter().append("g")
      .on("mouseover", function(event, d){
        console.log('in mouseover: ' + d.name );
        
  var message = `<dl><dt>${d.name}</dt>`;
        // d.listeners.forEach(listener => {
  	    //   message += `<dt>${listener}</dt>`;    	
        // });
  			message += '</dl>';
        tooltip.html(message); 
        return tooltip.style("visibility", "visible");})
      .on("mousemove", function(event){
        console.log('in mousemove');
        return tooltip.style("top", (event.pageY-10)+"px").style("left",(event.pageX+10)+"px");})
      .on("mouseout", function(){
        console.log('in mouseout');
        return tooltip.style("visibility", "hidden");})
  		.call(d3.drag().on("drag", dragged));

      var cityG = node.append("g").attr("class", (d)=>d.name);
     
  var image = cityG.append("svg:image")
        .attr("xlink:href", (d)=>d.isStartingCity?"/resources/images/starting_city.png":"/resources/images/white_city.png")
        .attr("class", "img")
        .attr("id",function(d){"c"+d.id})
        .attr("x",  -60)
        .attr("y",  -60)
        .attr("width", "120")
        .attr("height", "120")
        
            

      //   var positionC = svg.selectAll(".cityG").data(network.nodes)
      //   .append("text").text(function(d) {
      //   return d.name;
      // })
      // .attr('fill' , "white")
  		// .attr("text-anchor", "middle")
  		// .attr('y', -circleRadius-15);


  var labels = cityG.append("text")
      .text(function(d) {
        return d.name;
      })
      .attr('fill' ,  (d)=>{return d.isStartingCity?"#fff":"#000" })
  		.attr("text-anchor", "middle")
  		.attr("font-size", 10)
  		.attr('y', -20);

      var vp = cityG.append("text")
      .text(function(d) {
        return d.vp;
      })
      .attr('fill' , (d)=>{return d.isStartingCity?"#fff":"#000" })
  		.attr("text-anchor", "middle")
  		.attr('x', 40)
      .attr("y",30);

  var div = svg.append("div")	
      .attr("class", "tooltip")				
      .style("opacity", 0);

  var simulation = d3.forceSimulation()
      .force("link", d3.forceLink().id(function(d) { return d.id; }))
      .force("charge", d3.forceManyBody())
  		
      .force("center", d3.forceCenter(width / 2, height / 2)
      .strength(forceStrength));

      for (let i = 0; i < cities.length; i++) {
        var city = cities[i]
        var gg=".cityG"+city.id
        var singlecity = d3.select(gg)
        let capacity = city.capacity
        for (let positionIndex = 0; positionIndex < capacity; positionIndex++) {
          const color = city.isStartingCity?"#fff":"#000"  
          var position = singlecity.append("circle")
        .attr("r", positionRadius)
        .attr("stroke" , lineColor)
        .attr("stroke-width","1")
        .attr("fill",color)
        .attr("cx",(()=>{
          let c = city.capacity
          let r = positionRadius
          let i = positionIndex
          console.log(city.name)
          console.log("c:"+c)
          console.log("i"+i)
          return (i*2*r)-((c-1)*r)
          // return positionIndex*50

        }))
        .attr("cy",0)
      
      }
      }


  // var circle = circleG.append("circle") 
  //     .attr("r", circleRadius)
  //     .attr("fill", nodeColor); 
 async function addPositionsToCity(city,cityGroup) {
    let capacity = city.capacity
    for (let positionIndex = 0; positionIndex < capacity; positionIndex++) {
      const color = city.isStartingCity?"#fff":"#000"  
      var position = cityGroup.append("circle")
      .attr("r", positionRadius)
      .attr("stroke" , strokeColor)
      .attr("stroke-width","1")
      .attr("fill","none")
      .attr("cx",(()=>{
        let c = city.capacity
        let r = 2*positionRadius+5
        let i = positionIndex
        console.log(city.name)
        console.log("c:"+c)
        console.log("i"+positionIndex)
        return (i*2*r)-((c-1)*r)
        // return -8*positionRadius*(1-cap)+positionRadius*(1-cap)*offset
      }))
      .attr("cy",0).on("click", function() { window.open("https://media.criticalhit.net/2021/08/Halo-tv-series.jpg"); })
 
      
    }
 }



  simulation
      .nodes(network.nodes)
      .on("tick", ticked);

  simulation.force("link")
      .links(network.links)
  		.strength(linkStrength);

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
        .x(function(d) { return d.x; })
        .y(function(d) { return d.y; }));

    node
        .attr("transform", function(d) {
          return "translate(" + d.x + "," + d.y + ")";
        });
  }
  d3.selectAll("#GRACKLSTUGH").append('circle').attr("r", positionRadius)
      .attr("stroke" , strokeColor)
      .attr("stroke-width","1")
      .attr("fill","red")
}(d3));
</script>

</petclinic:layout>