package com.practice.kotlinwork.presentation

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.practice.kotlinwork.R

import com.practice.kotlinwork.model.PhotoFarm
import com.practice.kotlinwork.model.PhotoMainObject
import com.practice.kotlinwork.presentation.detail.ItemDetailActivity
import com.practice.kotlinwork.presentation.detail.ItemDetailFragment
import com.practice.kotlinwork.presentation.search.SearchActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    val itemListViewModel : ItemListViewModel by viewModel()
    lateinit var mRecyclerView : RecyclerView

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
     val reqCode: Int = 100
     val resCode: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->

            val intent = Intent(this, SearchActivity::class.java)
            startActivityForResult(intent, reqCode)

            Snackbar.make(view, "Show Search Activity", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        setupRecyclerView(item_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        mRecyclerView = recyclerView

        itemListViewModel.getRecentPhotos().observe( this, Observer {
            it.toString()
            recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, it?.photos?.allPhotos, twoPane)
        })
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity,
                                        private var values: List<PhotoFarm>?,
                                        private val twoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        fun setValues(value : List<PhotoFarm>?){
            values = value
            notifyDataSetChanged()
        }

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as PhotoFarm
                if (twoPane) {
                    val fragment = ItemDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ARG_ITEM_TITLE, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ARG_ITEM_TITLE, item.title)
                        putExtra(ItemDetailFragment.ARG_ITEM_URL, generateBIGImageURL(item))
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values?.get(position)
            holder.idView.text = item!!.title

            Picasso.with(this.parentActivity).load(generateImageURL(item))
                    .placeholder(R.mipmap.ic_launcher_round)// optional
                    .error(R.mipmap.ic_launcher_round)// optional
                    .into(holder.imageView);

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values!!.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.tv_content
            val imageView: ImageView = view.img_content
        }

        fun generateImageURL(item : PhotoFarm) : String{
            return "https://farm${item.farm}.staticflickr.com/${item.server}/${item.id}_${item.secret}_q.jpg"
        }

        fun generateBIGImageURL(item : PhotoFarm) : String{
            return "https://farm${item.farm}.staticflickr.com/${item.server}/${item.id}_${item.secret}_${item.size}.jpg"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val objectData = data?.extras?.get("CompleteObject") as PhotoMainObject
        val adapter = mRecyclerView.adapter as SimpleItemRecyclerViewAdapter
        adapter.setValues(objectData.photos?.allPhotos)

    }
}
