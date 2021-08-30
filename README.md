# LGU+ Watching Service


# Hexagonal Architecture
The hexagonal architecture divides the system into loosely-coupled interchangeable components; such as application core, user interface, data repositories, test scripts and other system interfaces etc.

In this approach, the application core that contains the business domain information and it is surrounded by a layer that contains adapters handling bi-directional communication with other components in a generic way.

![Hexagonal Architecture](./pictures/hexagonal_architecture3.png "Hexagonal Architecture")

#### Why Use Hexagonal Architecture ?
This pattern allows to isolate the core logic of application from outside concerns. Having the core logic isolated means you can easily change data source details <b>without a significant impact or major code rewrites to the codebase</b>. Your business rules simply don’t know anything at all about the outside world.

# Improvement Points
+ Make source code more testable, manageable and easily maintainable

![Img 01](./pictures/img_01.png "Hexagonal Architecture")

+ Correct NamingConvention for `methods`, `classes`

![Img 05](./pictures/img_05.png "Hexagonal Architecture")

+ Use `@Cachable` annotation instead of `RedisTemplate`

![Img 03](./pictures/img_03.png "Hexagonal Architecture")

+ Good performance

![Img 04](./pictures/img_04.png "Hexagonal Architecture")

<b>NOTE</b>: It is easy to change to new architecture

# Structure

![Project Structure](./pictures/structure.png "Hexagonal Architecture")

The project package structure also reflects the service architecture.

## 1) domain
The domain is the core of the hexagon, containing primary business logic

- com.fleta.watchingservice.domain.*

## 2) adapters
Adapters are either external APIs of your application or clients to other systems. They translate the interfaces of external systems (e.g. a search index or a file server API) to the interfaces required or exposed by the domain. Those interfaces are called ports.

- com.fleta.watchingservice.adapter.*

## 3) ports
An input port (driving port) lets the application core to expose the functionality to the outside of the world.

An output port (driven port) is another type of interface that is used by the application core to reach things outside of itself (like getting some data from a database).

### *) Redis
Describe how to apply `Redis` into this project
- 구현
    - com.fleta.watchingservice.config.DefaultRedisCacheConfiguration
        - @EnableCaching으로 Cache 사용을 활성화 합니다. cacheManager의 configuration을 통해 캐시 정책을 변경 합니다.
    - @Cachable
        - Redis에 캐싱된 데이터가 있으면 반환하고, 없으면 DB에서 조회한다음 Redis에 캐시합니다. 어노테이션은 Method나 Type에 세팅할 수 있고 런타임에 수행됩니다.
    
## References
- https://medium.com/javarevisited/hexagonal-architecture-in-java-9031d3570d15
- https://www.expatdev.com/posts/hexagonal-architecture-in-java/
- https://www.freecodecamp.org/news/implementing-a-hexagonal-architecture/
- https://daddyprogrammer.org/post/3217/redis-springboot2-spring-data-redis-cacheable-cacheput-cacheevict/
