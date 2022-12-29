let clickSnd = new Audio("sounds/click.wav")
let intervalId = null
let clientId = null
let peer = null
let connections = []
let roomId = null
initialize()

$("#play").click(() => sendMessage("play"))
$("#stop").click(() => sendMessage("stop"))
$("#connect").click(() => connectRoom($("#roomId").val()))
$("#disconnect").click(disconnectRoom)

function initialize() {
    peer = new Peer()

    peer.on("open", (id) => {
        clientId = id
        console.log("ID: " + id)
    })

    peer.on("connection", conn => {
        console.log("Connected from: " + conn.peer)
        conn.on("data", (data) => receiveMessage(conn, data))
        connections.push(conn)
        connectClient(conn.peer)
        printClients(connections.map(it => it.peer))
    })

    $(window).on("beforeunload", function(evt) {
        evt.preventDefault()
        evt.returnValue = ""

        $.post("/rooms/" + roomId + "/disconnect/" + clientId)
        sendMessage("remove")

        return null
    })
}

function connectClient(id) {
    if (id === clientId || connections.map(it => it.peer).includes(id)) return

    let conn = peer.connect(id, { reliable: true })
    conn.on("open", () => console.log("Connected to: " + conn.peer))
    conn.on("data", (data) => receiveMessage(conn, data))

    connections.push(conn)
}

function connectRoom(id) {
    if (roomId) return

    $.post("/rooms/" + id + "/connect/" + clientId, (clients) => {
        clients.forEach(id => connectClient(id))
        printClients(clients)

        $("#room").show()
        roomId = id
    })
}

function disconnectRoom() {
    if (!roomId) return

    $.post("/rooms/" + roomId + "/disconnect/" + clientId)
    sendMessage("remove")

    roomId = null
    $("#room").hide()
    $("#info").html("Disconnected")
}

function printClients(clients) {
    if (clients.length > 0) $("#info").html(clients.join("<br>"))
    else $("#info").html("There's only you here")

    console.log("clientId = " + clientId)
    console.log("connections = " + connections.map(it => it.peer))
}

function sendMessage(data) {
    if (intervalId != null) clearInterval(intervalId)

    switch (data) {
        case "play":
            intervalId = setInterval(() => {
                connections.forEach(conn => conn.send(data))
                clickSnd.play()
            }, 60_000 / $("#bpm").val())
            break
        case "stop":
            connections.forEach(conn => conn.send(data))
    }
}

function receiveMessage(conn, data) {
    switch (data) {
        case "play":
            clickSnd.play()
            break
        case "stop":
            if (intervalId != null) clearInterval(intervalId)
            break
        case "remove":
            connections = connections.filter(it => it.peer !== conn.peer)
    }
}
