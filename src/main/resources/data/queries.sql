select
    product_id,
    sum(m2) - ( seLect sum(m2) from events where product_id = '1' and event_type = 'SALE' )  as m2,
    sum(weight) - ( seLect sum(weight) from events where product_id = '1' and event_type = 'SALE' ) as weight,
    count(*) - ( seLect count(*) from events where product_id = '1' and event_type = 'SALE' ) as count
from events
where product_id = '125' and event_type = 'IN'
group by product_id
order by product_id asc;

select
        sum(m2) -     ( seLect sum(m2)      from events where event_type = 'SALE' ) as m2,
        sum(weight) - ( seLect sum(weight)  from events where event_type = 'SALE' ) as weight,
        count(*) -    ( seLect count(*)     from events where event_type = 'SALE' ) as count
from events
where event_type = 'IN';


select
       product_id as id,
       sum( m2) as m2,
       count(*) as count,
       sum(weight) as weight,
       sum(pieces) as pieces,
       clock_timestamp () as date,
       clock_timestamp () as modified,
       avg(1) as serial,
       avg(2) as ean,
       avg(3) as width,
       avg(4) as height,
       avg(5) as description,
       avg(7) as notes,
       avg(1) as user_id,
       'IN' as event_type,
       product_id as product_id
from events
where serial not in (select serial from events where event_type = 'SALE')
group by product_id
order by id asc;


select
    product_id as id,
    sum( m2) as m2,
    count(*) as count,
    sum(weight) as weight,
    sum(pieces) as pieces,
    clock_timestamp () as date,
    clock_timestamp () as modified,
    avg(1) as serial,
    avg(2) as ean,
    avg(3) as width,
    sum(height) as height,
    avg(5) as description,
    avg(7) as notes,
    avg(1) as user_id,
    'SALE' as event_type,
    avg(1) as customer_id,
    avg(1) as invoice_id,
    product_id as product_id
from saleevents
where invoice_id = '11'
group by product_id
order by id asc;

