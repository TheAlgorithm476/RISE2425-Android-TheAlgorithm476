package space.astar_technologies.app.mock

import kotlinx.datetime.LocalDateTime
import space.astar_technologies.app.data.Article
import space.astar_technologies.app.data.Launch
import space.astar_technologies.app.data.Rocket

object MockDataSource {
    val rockets: List<Rocket> = listOf(
        Rocket(
            id = 1,
            name = "A-1",
            description = "The A-1 is a line-up of Model Rockets designed by Astar Technologies.\n\nThe A-1 is Astar's first ever design, and is designed to be reusable by performing a retropropulsive landing burn.",
            diameter = 10,
            status = "Active",
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 Prototype A-1 in flight"
        ),
        Rocket(
            id = 8,
            name = "A-1 Vehicle 4",
            height = 100,
            description = "A-1 Vehicle 4 is the first A-1 model to use the new \"A-1 Vehicle x\" naming scheme, instead of the \"Mkx\" naming scheme used for prior models. It draws from the lessons learned in the first flight test, and features numerous upgrades.",
            diameter = 10,
            status = "Planned",
            boosters = 3,
            imageUrl = "https://astar-technologies.space/a1_mk2.png",
            imageDescription = "Placeholder",
            parentId = 1
        ),
        Rocket(
            id = 2,
            name = "Mk1 Prototype A-1",
            height = 75,
            description = "The Mk1 Prototype A-1 is the first model that got conceptualised. It features a 9cm-diameter PVC body, and PVC landing legs.\n\nMk1 was originally slated to be part of Astar's first launch, but as the design goals of the A-1 grew, it quickly became out-of-date, before eventually being replaced by newer models.",
            diameter = 9,
            status = "Retired",
            boosters = 0,
            imageUrl = "https://astar-technologies.space/IMG_1059.png",
            imageDescription = "Mk1 Prototype A-1",
            parentId = 1
        ),
        Rocket(
            id = 3,
            name = "Mk2 Prototype A-1",
            height = 100,
            description = "The Mk2 Prototype A-1 marked the shift from PVC housing to 3D-printed housings. Mk2 features 3 ring sections, separated by stiffener rings.\n\nMk2 got irrepairably damaged during transport operations.",
            diameter = 10,
            status = "Destroyed",
            boosters = 0,
            imageUrl = "https://astar-technologies.space/IMG_1223.png",
            imageDescription = "Mk2 Prototype A-1 next to Mk1, with Mk2.2 in the background",
            parentId = 1
        ),
        Rocket(
            id = 4,
            name = "Mk2.1",
            height = 25,
            description = "Mk2.1 was a structural test article meant to verify Mk2's design. It performed the first burst test of any A-1 prototype, but it failed to maintain pressure.",
            diameter = 10,
            status = "Retired",
            imageUrl = "https://astar-technologies.space/a1_mk2.png",
            imageDescription = "Placeholder",
            parentId = 3
        ),
        Rocket(
            id = 5,
            name = "Mk2.2",
            height = 25,
            description = "Mk2.2 was a structural test article, meant to verify a new 3D printer slicer setting.",
            diameter = 10,
            status = "Retired",
            imageUrl = "https://astar-technologies.space/IMG_3333.png",
            imageDescription = "Mk2.2",
            parentId = 3
        ),
        Rocket(
            id = 6,
            name = "Mk3 Prototype A-1",
            height = 120,
            description = "The Mk3 Prototype A-1 was the first A-1 model that finished construction, and the first model to perform a flight test. It replaced Mk2's PLA body with a strong ABS body, as well as a more integrated inter-tank structure.\n\nMk3 performed the first flight test of an A-1 rocket.",
            liftoffThrust = 44,
            diameter = 10,
            status = "Expended",
            boosters = 3,
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 Prototype A-1 in flight",
            parentId = 1
        ),
        Rocket(
            id = 7,
            name = "Mk3.1",
            height = 35,
            description = "Mk3.1 was a miniature version of Mk3, consisting of 1 tank section (instead of 5), and barebones landing legs. It performed 2 burst tests before being retired.",
            diameter = 10,
            status = "Retired",
            imageUrl = "https://astar-technologies.space/IMG_3334.png",
            imageDescription = "Mk3.1 after its retirement",
            parentId = 6
        )
    )

    val launches: List<Launch> = listOf(
        Launch(
            id = 1,
            name = "TF-1",
            date = LocalDateTime(2024, 9, 28, 15, 0, 0),
            dateIsNET = false,
            description = "First flight of any A-1 rocket. First launch organised by Astar.\nAfter liftoff, heavy winds caught the rocket, and drastically changed its course, causing it to crash into the ground seconds later. All primary objectives of the mission were achieved however.",
            success = true,
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 Prototype A-1 in flight",
            rocketId = rockets.find { it.id == 6 }!!.id,
            rocket = rockets.find { it.id == 6 }!!
        ),
        Launch(
            id = 2,
            name = "Flight 2",
            dateIsNET = true,
            description = "Second flight of the A-1. First flight of a non-prototype A-1 vehicle. First landing attempt of the A-1.\nThe launch draws from the lessons learned during TF-1, and features numerous changes on both ground and launch vehicle.",
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 Prototype A-1 in flight",
            rocketId = rockets.find { it.id == 8 }!!.id,
            rocket = rockets.find { it.id == 8 }!!
        ),
        Launch(
            id = 3,
            name = "Demo Launch 1",
            dateIsNET = true,
            description = "Demo Launch to show some launches on the Android app",
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 Prototype A-1 in flight",
            rocketId = rockets.find { it.id == 8 }!!.id,
            rocket = rockets.find { it.id == 8 }!!
        ),
        Launch(
            id = 4,
            name = "Demo Launch 2",
            dateIsNET = false,
            description = "Demo Launch to show some launches on the Android app",
            success = false,
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 Prototype A-1 in flight",
            rocketId = rockets.find { it.id == 6 }!!.id,
            rocket = rockets.find { it.id == 6 }!!
        )
    )

    val articles: List<Article> = listOf(
        Article(
            id = 1,
            title = "Preparing for TF-1",
            author = "Brent Van den Abbeel",
            datePublished = LocalDateTime(2024, 8, 16, 18, 32, 0),
            lastEdited = LocalDateTime(2024, 8, 16, 18, 32, 0),
            content = "Astar is gearing up for its first launch, Test Flight 1 *(TF-1)*, which will showcase the A-1 launch system. This milestone event marks the beginning of a series of flight tests aimed at validating the A-1 design, and assessing Astar's readiness and ability to manage a model rocket launch.\n\nTF-1 is not just a routine simulation; it involves actual flight hardware and carries inherent risks. While the test aims to provide important data, there are no guarantees of success. Our primary goals are to ensure a smooth liftoff, avoiding damage to ground systems, and to maintain a stable ascent.\nThe centerpiece of the flight test is the Mk3 Prototype A-1, the third iteration of the A-1 model rocket family, and the first flight-capable model. Outfitted with 3 solid rocket boosters, Mk3 is designed to lift its entire structure off the ground. Mk3 features no active guidance, onboard sensors, or engine controllers. Once the ground commits to a liftoff, the mission will proceed with no possibility of turning back.\n\n![Mk3 Prototype A-1 without its side boosters attached](https://astar-technologies.space/mk3.png)\n\nIn addition to testing the rocket's hardware, TF-1 offers a unique opportunity for bystanders to witness the launch up close. External visitors are invited to observe the launch from a close but safe distance, providing a rare insight into the excitement of rocketry in action.\n\nBeyond testing the hardware, TF-1 will also serve as a proving ground for Astar, providing invaluable experience in managing the complexities of a live launch. The experience gained from this mission will impact future developments, and refine Astar's approach to mission design and planning.\n\nAstar would like to thank every external party involved for making this dream come true. We appreciate your continued patience and support in the project.",
            imageUrl = "https://astar-technologies.space/tf-1/IMG_0836.jpg",
            imageDescription = "Mk3 Prototype A-1 post-flight",
            rocketId = rockets.find { it.id == 6 }!!.id,
            rocket = rockets.find { it.id == 6 }!!,
            launchId = launches.find { it.id == 1 }!!.id,
            launch = launches.find { it.id == 1 }!!.let { Article.ArticleLaunch(it.id, it.name, it.date, it.dateIsNET, it.description, it.success, it.imageUrl, it.imageDescription, it.rocket.let { Article.ArticleRocket(it.id) }) }
        ),
        Article(
            id = 2,
            title = "A TF-1 Announcement",
            author = "Brent Van den Abbeel",
            datePublished = LocalDateTime(2024, 9, 13, 18, 32, 0),
            lastEdited = LocalDateTime(2024, 9, 13, 18, 32, 0),
            content = "*Test Flight 1 (TF-1)*, Astar's first flight of the A-1 model rocket, may launch as soon as **September 28th, 2024**, pending weather.\n\nA backup opportunity is available 8 days later, on **October 6th**, in case the weather doesn't allow for a launch. Both windows open on 2:00 PM GMT+2, and close 3 hours later, at 5:00 PM.\n\nPeople closest to the project have been invited to join Astar on this mission. The people invited have received an e-mail with instructions on when and where they will be expected.\n\nAstar would once again like to thank everyone involved in making this dream come true. Thanks for your support.",
            imageUrl = "https://astar-technologies.space/mk3.png",
            imageDescription = "Mk3 Prototype A-1 without its side boosters attached",
            rocketId = rockets.find { it.id == 6 }!!.id,
            rocket = rockets.find { it.id == 6 }!!,
            launchId = launches.find { it.id == 1 }!!.id,
            launch = launches.find { it.id == 1 }!!.let { Article.ArticleLaunch(it.id, it.name, it.date, it.dateIsNET, it.description, it.success, it.imageUrl, it.imageDescription, it.rocket.let { Article.ArticleRocket(it.id) }) }
        ),
        Article(
            id = 3,
            title = "TF-1 Photo Collection",
            author = "Brent Van den Abbeel",
            datePublished = LocalDateTime(2024, 10, 23, 18, 32, 0),
            lastEdited = LocalDateTime(2024, 10, 23, 18, 32, 0),
            content = "On September 28th, 2024, Astar Technologies conducted its first ever model rocket launch, TF-1, where the Mk3 Prototype A-1 got its chance to shine bright.\n\nAll 3 SRBs ignited successfully, followed by liftoff shortly after. Mk3 reached an apogee of about 22 meters, before wind tipped it over and caused it to travel back to the ground, before impacting it shortly after.\n\nAstar considers this Flight Test a big success. All major test goals got achieved, and the launch gave us a treasure trove of valuable, real-life data, that will be used to improve A-1 reliability from Flight 2 onwards.\n\nWe would like to thank everyone who was present at the launch, and as promised, some of our best photos from the event are visible right here, on your screen. All pictures were taken by my good friend a photographer, Quinten. Astar hopes to welcome you all again for a future event.\n\n![Photo ](https://astar-technologies.space/tf-1/IMG_0728.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0740.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0743.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0756.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0760.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0763.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0779.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0795.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0804.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0819.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0822.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0824.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0826.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0836.jpg) ![Photo ](https://astar-technologies.space/tf-1/IMG_0844.jpg)",
            imageUrl = "https://astar-technologies.space/mk3_in_flight.png",
            imageDescription = "Mk3 Prototype A-1 in flight",
            rocketId = rockets.find { it.id == 6 }!!.id,
            rocket = rockets.find { it.id == 6 }!!,
            launchId = launches.find { it.id == 1 }!!.id,
            launch = launches.find { it.id == 1 }!!.let { Article.ArticleLaunch(it.id, it.name, it.date, it.dateIsNET, it.description, it.success, it.imageUrl, it.imageDescription, it.rocket.let { Article.ArticleRocket(it.id) }) }
        )
    )
}